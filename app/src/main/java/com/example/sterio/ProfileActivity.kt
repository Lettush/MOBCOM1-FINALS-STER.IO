package com.example.sterio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.sterio.databinding.ActivityLoginBinding
import com.example.sterio.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    // ViewBinding
    private lateinit var binding:ActivityProfileBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure ActionBar
        //actionBar = supportActionBar!!
        //actionBar.title = "Profile"

        // Init FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // Handle Logout action
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // User is logged in
            val email = firebaseUser.email

            // Set textView to email
            binding.lblUsername.text = email
        } else {
            // User is not logged in
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}