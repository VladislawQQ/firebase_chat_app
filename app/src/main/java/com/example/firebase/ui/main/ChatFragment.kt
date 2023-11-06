package com.example.firebase.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.databinding.FragmentChatBinding
import com.example.firebase.model.MessageItem
import com.example.firebase.model.User
import com.example.firebase.ui.Constants
import com.example.firebase.ui.Constants.imagesStorage
import com.example.firebase.ui.adapter.MessageAdapter
import com.example.firebase.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val viewModel : ChatViewModel by viewModels()

    private val msgAdapter = MessageAdapter()
    private var userName : String = "Default Name"
    private lateinit var pickMedia : ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage

    private lateinit var messagesDatabaseRef: DatabaseReference
    private lateinit var usersDatabaseRef: DatabaseReference
    private lateinit var imagesStorageRef: StorageReference

    private val messagesChildEventListener = (object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val message = snapshot.getValue(MessageItem::class.java)
            viewModel.addMessage(message)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("onCancelled : ", error.toString())
        }
    })

    private val usersChildEventListener = (object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val user = snapshot.getValue(User::class.java)

            if (user?.id == FirebaseAuth.getInstance().currentUser?.uid) {
                userName = user?.name ?: userName
            }

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("onCancelled : ", error.toString())
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initImagePicker()

        setFirebase()
        setListeners()

        enableBtnWhenEditTextNotEmpty()
        bindRecycleView()
        observeViewModel()
    }

    private fun initImagePicker() {
        pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                val imagesReference = imagesStorageRef.child(uri.lastPathSegment.toString())
                val uploadTask = imagesReference.putFile(uri)

                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imagesReference.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result

                        val message = MessageItem(
                            name = userName,
                            imgUrl = downloadUri.toString()
                        )

                        messagesDatabaseRef.push().setValue(message)
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    private fun setFirebase() {
        // Write a message to the database
        database = Firebase.database
        storage = Firebase.storage

        messagesDatabaseRef = database.reference.child(Constants.messagesDB)
        messagesDatabaseRef.addChildEventListener(messagesChildEventListener)

        usersDatabaseRef = database.reference.child(Constants.usersDB)
        usersDatabaseRef.addChildEventListener(usersChildEventListener)

        imagesStorageRef = storage.reference.child(imagesStorage)
    }

    private fun setListeners() {
        with(binding) {
            btnSendMessage.setOnClickListener {
                val item = MessageItem(
                    editTextMessage.text.toString(),
                    userName,
                    null
                )

                messagesDatabaseRef.push().setValue(item)
                editTextMessage.text?.clear()
            }

            imageViewLogOut.setOnClickListener {
                logout()
            }

            imageViewSelectImage.setOnClickListener {
                pickImage()
            }

            backPressedListener()
        }
    }

    private fun logout() {
        Firebase.auth.signOut()
        openAuthActivity()
    }

    private fun pickImage() {
        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    private fun backPressedListener() {
        val callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                logout()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }

    private fun openAuthActivity() {
        val direction = ChatFragmentDirections
            .actionChatFragmentToAuthActivity()

        navController.navigate(direction)
    }

    private fun enableBtnWhenEditTextNotEmpty() {
        with(binding) {
            editTextMessage.doOnTextChanged() { _, _, _, _ ->
                btnSendMessage.isEnabled = !editTextMessage.text.isNullOrEmpty()
            }
        }
    }

    private fun bindRecycleView() {
        val recyclerLayoutManager = LinearLayoutManager(activity)

        with(binding.rvMessages) {
            layoutManager = recyclerLayoutManager
            adapter = msgAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messagesStateFlow.collect { messages ->
                    msgAdapter.submitList(messages)
                }
            }
        }
    }

}