package com.example.firebaseauth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signInButton.setOnClickListener {
            signIn(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
        binding.createAccountButton.setOnClickListener {
            createAccount(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
        binding.signOutButton.setOnClickListener {
            signOut()
        }
        binding.verifyEmailButton.setOnClickListener {
            sendEmailVerification()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.statusTextView.text = getString(R.string.emailpassword_status_fmt, user.email, user.isEmailVerified)
            binding.detailTextView.text = getString(R.string.firebase_status_fmt, user.uid)
            binding.signInSection.visibility = View.GONE
            binding.signedInSection.visibility = View.VISIBLE
            binding.verifyEmailButton.isEnabled = !user.isEmailVerified
        } else {
            binding.statusTextView.text = getString(R.string.signed_out)
            binding.detailTextView.text = null
            binding.signInSection.visibility = View.VISIBLE
            binding.signedInSection.visibility = View.GONE
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount: $email")
        if (!validateForm()) {
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail: success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail: failure", it.exception)
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.emailEditText.text.toString()
        if (email.isEmpty()) {
            binding.emailEditText.error = "Required."
            valid = false
        } else {
            binding.emailEditText.error = null
        }

        val password = binding.passwordEditText.text.toString()
        if (password.isEmpty()) {
            binding.passwordEditText.error = "Required."
            valid = false
        } else {
            binding.passwordEditText.error = null
        }

        return valid
    }


    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn: $email")
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail: success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail: failure", it.exception)
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                if (!it.isSuccessful) {
                    binding.statusTextView.text = getString(R.string.auth_failed)
                }
            }
    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        binding.verifyEmailButton.isEnabled = false

        val user = mAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                binding.verifyEmailButton.isEnabled = true

                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Verification email sent to ${user.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.w(TAG, "sendEmailVerification", it.exception)
                    Toast.makeText(
                        this,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}