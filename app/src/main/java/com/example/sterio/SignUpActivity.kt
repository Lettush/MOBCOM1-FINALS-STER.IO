package com.example.sterio

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.sterio.databinding.ActivityLoginBinding
import com.example.sterio.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    // Viewbinding
    private lateinit var binding: ActivitySignUpBinding

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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuring the Action Bar
        //actionBar = supportActionBar!!
        //actionBar.title = "Sign Up"

        // Enable back button
        //actionBar.setDisplayHomeAsUpEnabled(true)
        //actionBar.setDisplayShowHomeEnabled(true)

        // Actionbar
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "Create an Account"

        // FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Handle Signup, opens SignUpActivity
        binding.btnSignup.setOnClickListener {
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
        } else if (TextUtils.isEmpty(email)) {
            binding.txtPassword.error = "Please enter a username"
        } else if (TextUtils.isEmpty(password)) {
            binding.txtPassword.error = "Please enter a password"
        } else if (password.length < 8){
            binding.txtPassword.error = "Password is too short. Must be at least 8 characters."
        } else {
            // Credentials are valid
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        progressBar = ProgressBar(this, null, R.attr.progressBarStyleLarge)
        progressBar.visibility = View.VISIBLE;

        // Create an Account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                progressBar.visibility = View.GONE;
                // Sign up success
                Toast.makeText(this, "Successfully created account with $email", Toast.LENGTH_SHORT).show()

                // Gets current user
                //val firebaseUser = firebaseAuth.currentUser
                //val email = firebaseUser!!.email

                // Opens LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                // Sign up failed
                progressBar.visibility = View.GONE;
                Toast.makeText(this, "Sign up failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // returns to previous activity
        return super.onSupportNavigateUp()
    }
}