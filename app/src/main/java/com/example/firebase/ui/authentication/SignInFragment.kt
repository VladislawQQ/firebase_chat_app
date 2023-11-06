package com.example.firebase.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.firebase.databinding.FragmentSignInBinding
import com.example.firebase.model.User
import com.example.firebase.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        if (auth.currentUser != null) {
            openMainActivity()
        }

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            buttonSingIn.setOnClickListener {
                    signInUser(
                        editTextEmail.text.toString().trim(),
                        editTextPassword.text.toString().trim()
                    )
            }

            textViewTapToSignUp.setOnClickListener {
                openSignUpFragment()
            }
        }
    }

    private fun signInUser(email : String, password : String) {
        activity?.let {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)

                        openMainActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            it,
                            "Email or password is incorrect",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }
        }
    }

    private fun openMainActivity() {
        val direction = SignInFragmentDirections
            .actionSignInFragmentToMainActivity()

        navController.navigate(direction)
    }

    private fun openSignUpFragment() {
        val direction = SignInFragmentDirections
            .actionSignInFragmentToSignUpFragment()

        navController.navigate(direction)
    }

    companion object {
        private const val TAG = "signIn"
    }


}