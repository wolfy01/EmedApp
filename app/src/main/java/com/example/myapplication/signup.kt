package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var username=findViewById<EditText>(R.id.user_name1)
        var password=findViewById<EditText>(R.id.sign_password)
        var conpassword=findViewById<EditText>(R.id.confirm_password)
        var button_signup=findViewById<Button>(R.id.signup_btn)
        auth = Firebase.auth
        db= FirebaseDatabase.getInstance().getReference("Users")
        button_signup.setOnClickListener{
            val email=username.text.toString()
            val pass=password.text.toString()
            val copass=conpassword.text.toString()
            if(email.isEmpty()){
                username.setError("Enter an e-mail address")
                username.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                username.setError("Enter an valid e-mail address")
                username.requestFocus()
                return@setOnClickListener
            }
            if(pass.isEmpty()){
                password.setError("Enter a password")
                password.requestFocus()
                return@setOnClickListener
            }
            if(pass.length<6){
                password.setError("Password must have minimum length of 6")
                password.requestFocus()
                return@setOnClickListener
            }
            if(!pass.equals(copass)){
                password.setError("Wrong password")
                password.requestFocus()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val index = email.indexOf('@')
                        val username: String? = if (index == -1) null else email.substring(0,index)
                        val user=Usertype(username,"patient")
                        if (username != null) {
                                db.child(username).setValue(user).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}