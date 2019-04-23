package com.example.starmark

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{
            performLogin()
        }

        backToRegister.setOnClickListener {
            finish()
        }
    }

    private fun performLogin(){
        val email = loginEmailEditText.text.toString()
        val password = loginPasswordEditText.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter username / password", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                var id = ""
                if(it.isSuccessful){
                    val ref = FirebaseFirestore.getInstance()

                    ref.collection("users")
                        .get()
                        .addOnSuccessListener {result ->
                            for(document in result){
                                if(document["email"] == email){
                                    id = document["id"].toString()
                                    break
                                }
                            }
                        }
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    intent.putExtra("userId", id)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to login. Message: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
