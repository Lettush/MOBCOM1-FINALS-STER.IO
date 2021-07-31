package com.example.sterio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sterio.databinding.ActivityProfileBinding
import com.example.sterio.databinding.ActivityReviewsBinding
import com.google.firebase.auth.FirebaseAuth
import java.sql.DatabaseMetaData

class ReviewsActivity : AppCompatActivity() {
    // ViewBinding
    private lateinit var binding: ActivityReviewsBinding

    // Database Helper
    private lateinit var db: DataBaseHelper;

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DataBaseHelper(applicationContext)

        // Display data when app starts
        val data = db.readData()

        binding.rvData.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvData.adapter = CustomListAdapter(data)

        // Actionbar
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "Fish Tail - Tyler The Creator"
    }

    fun insertData(v: View) {
        if (binding.editTextReview.text.toString().isNotEmpty() &&
                binding.editTextRating.text.toString().isNotEmpty()) {

            // Inserts to the database
            val user = User(0, binding.editTextReview.text.toString(), binding.editTextRating.text.toString().toInt())
            db.insertData(user)

            // Toast
            Toast.makeText(this, "Review Posted", Toast.LENGTH_LONG).show()
            clearField()
        } else {
            // If empty
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData (v: View) {
//        val data = db.readData()
//        binding.tvResult.text = ""
//
//        // Displays data to ScrollView
//        for (i in 0 until data.size) {
//            binding.tvResult.append(
//                data[i].id.toString() + " - " + data[i].review + " - " + data[i].rating + " /10 \n "
//            )
//        }
    }

    fun clearField() {
        binding.editTextReview.text.clear()
        binding.editTextRating.text.clear()
        binding.editTextReview.hasFocus()
    }

    // Goes back to previous screen
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // returns to previous activity
        return super.onSupportNavigateUp()
    }
}