package com.example.starmark

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            updateUI()
        }

        registerButton.setOnClickListener {
            performRegistration()
        }

        alreadyHaveAccountTextView.setOnClickListener {
            Log.d("MainActivity", "Trying to change to login")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(){
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
    }

    private fun performRegistration(){
        val email = emailEditText.text.toString()
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()
        Log.d("MainActivity", "Email is $email")
        Log.d("MainActivity", "Username is $username")

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter email/password", Toast.LENGTH_SHORT).show()
            return
        }

        // Use FirebaseAuth to create user
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                // its successful
                Log.d("MainActivity", "Successfully created user with id" + it.result?.user?.uid)
                Toast.makeText(this, "You are registered.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Log.d("MainActivity", "Failed to create user. Message: ${it.message}")
                Toast.makeText(this, "Failed to create user. Message: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
