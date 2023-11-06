package com.example.firebase.ui.authentication

import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firebase.databinding.FragmentSignUpBinding
import com.example.firebase.model.User
import com.example.firebase.ui.Constants.usersDB
import com.example.firebase.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private lateinit var database : FirebaseDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        setFirebase()
        setListeners()
    }

    private fun setFirebase() {
        // Write a message to the database
        database = Firebase.database
        myRef = database.reference.child(usersDB)
    }

    private fun setListeners() {
        with(binding) {
            buttonSingUp.setOnClickListener {
                if (editTextPassword.text.toString().trim() == editTextRepeatPassword.text.toString().trim()) {
                    createUserAndSignUp(
                        editTextEmail.text.toString().trim(),
                        editTextPassword.text.toString().trim()
                    )
                } else {
                    Toast.makeText(
                        activity,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            textViewTapToLogin.setOnClickListener {
                openSignInFragment()
            }
        }
    }

    private fun createUserAndSignUp(email : String, password : String) {
        activity?.let {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        createUser(user)

                        openMainActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            it,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }
        }
    }

    private fun createUser(firebaseUser: FirebaseUser?) {
        val user = firebaseUser?.let {
            User(
                it.email.toString(),
                it.uid,
                binding.editName.text.toString()
            )
        }

        myRef.push().setValue(user)
    }

    private fun openMainActivity() {
        val direction = SignUpFragmentDirections
            .actionSignUpFragmentToMainActivity()

        navController.navigate(direction)
    }


    private fun openSignInFragment() {
        val direction = SignUpFragmentDirections
            .actionSignUpFragmentToSignInFragment()

        navController.navigate(direction)
    }

    companion object {
        private const val TAG = "signUp"
    }
}