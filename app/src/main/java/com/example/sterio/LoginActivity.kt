package com.example.sterio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.sterio.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Viewbinding
    private lateinit var binding: ActivityLoginBinding

    // Action bar
    private lateinit var actionBar: ActionBar

    // ProcessDialog
    private lateinit var progressBar: ProgressBar

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuring the Action Bar
        //actionBar = supportActionBar!!
        //actionBar.title = "Login"

        // Configure ProgressBar
        progressBar = ProgressBar(this)

        // FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // Handle Signup, opens SignUpActivity
        binding.txtSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        // Handle login action
        binding.btnLogin.setOnClickListener {
            // Validate credentials before logging in
            validateData()
        }
    }

    private fun validateData() {
        // Credentials
        email = binding.txtUsername.text.toString().trim()
        password = binding.txtPassword.text.toString().trim()

        // Validation
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Invalid email format
            binding.txtUsername.error = "Invalid email address"
        } else if (TextUtils.isEmpty(email)){
            binding.txtPassword.error = "Please enter a username"
        } else if (TextUtils.isEmpty(password)) {
            binding.txtPassword.error = "Please enter a password"
        } else {
            // Credentials are valid
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        progressBar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Login successful
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Login as $email", Toast.LENGTH_SHORT).show()

                // Opens auth screen
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            .addOnFailureListener { e->
                progressBar.visibility = View.GONE
                // Login Failed
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // User is logged in
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}