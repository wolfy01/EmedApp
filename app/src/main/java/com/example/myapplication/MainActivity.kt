package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var db1: DatabaseReference
    private lateinit var db2: DatabaseReference
    private lateinit var loadingBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        loadingBar = ProgressDialog(this)
        db= FirebaseDatabase.getInstance().getReference("Users")
        db1= FirebaseDatabase.getInstance().getReference("Blacklist")
        db2= FirebaseDatabase.getInstance().getReference("DoctorInfo")
        setContentView(R.layout.activity_main)
        var et_user_name = findViewById<EditText>(R.id.user_name)
        var et_password = findViewById<EditText>(R.id.et_password)
        var button_login= findViewById<Button>(R.id.login_btn)
        var button_signup=findViewById<Button>(R.id.signup_btn)
        var button_reset=findViewById<Button>(R.id.forget_btn)
        button_login.setOnClickListener {
            val email=et_user_name.text.toString()
            val pass=et_password.text.toString()
            if(email.isEmpty()){
                et_user_name.setError("Enter an e-mail address")
                et_user_name.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_user_name.setError("Enter an valid e-mail address")
                et_user_name.requestFocus()
                return@setOnClickListener
            }
            if(pass.isEmpty()){
                et_password.setError("Enter a password")
                et_password.requestFocus()
                return@setOnClickListener
            }
            if(pass.length<6){
                et_password.setError("Password must have minimum length of 6")
                et_password.requestFocus()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val index = email.indexOf('@')
                        val username: String? = if (index == -1) null else email.substring(0,index)
                        if (username != null) {
                            db.child(username).get().addOnSuccessListener { it1 ->
                                if(it1.exists()){
                                    val userid=it1.child("userid").value
                                    if (userid != null) {
                                        if(userid.equals("admin")){
                                            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this, admin::class.java))
                                            finish()
                                        }
                                        else if(userid.equals("doctor")){
                                            db2.child(username).get().addOnSuccessListener { it2 ->
                                                if(it2.exists()){
                                                    val uid=it2.child("id").value.toString()
                                                    val firstname=it2.child("firstname").value
                                                    db1.child(firstname as String).get().addOnSuccessListener {
                                                        if (it.exists()){
                                                            val uid1=it.child("id").value.toString()
                                                            if(uid==uid1){
                                                                db2.child(username).removeValue()
                                                                Toast.makeText(applicationContext, "Login unsuccessful", Toast.LENGTH_SHORT).show()
                                                            }
                                                            else{
                                                                Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                                                                startActivity(Intent(this, Doctor::class.java))
                                                                finish()
                                                            }
                                                        }
                                                        else{
                                                            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                                                            startActivity(Intent(this, Doctor::class.java))
                                                            finish()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else{
                                            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this, patient::class.java))
                                            finish()
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        Toast.makeText(applicationContext, "Login failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        button_signup.setOnClickListener{
            val intent= Intent(this, signup::class.java)
            startActivity(intent)
        }
        button_reset.setOnClickListener{
            val email=et_user_name.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(baseContext, "Email Required", Toast.LENGTH_SHORT).show()
            } else {
                loadingBar.setMessage("Sending email...")
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    loadingBar.dismiss()
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "A link has been sent to your email, please check.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}