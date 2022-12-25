package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddinDoctor : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var db1: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addin_doctor)
        var username=findViewById<EditText>(R.id.et_user_name)
        var password=findViewById<EditText>(R.id.et_password)
        var firstname=findViewById<EditText>(R.id.et_first_name)
        var lastname=findViewById<EditText>(R.id.et_last_name)
        var age=findViewById<EditText>(R.id.et_age)
        var practitioner=findViewById<EditText>(R.id.et_practitionerat)
        var rank=findViewById<EditText>(R.id.et_rank)
        var id=findViewById<EditText>(R.id.et_id)
        var offcieno=findViewById<EditText>(R.id.et_office_no)
        var submit=findViewById<Button>(R.id.btn_submit)
        auth = Firebase.auth
        db= FirebaseDatabase.getInstance().getReference("Users")
        db1= FirebaseDatabase.getInstance().getReference("DoctorInfo")
        submit.setOnClickListener{
            val email=username.text.toString()
            val pass=password.text.toString()
            val fname=firstname.text.toString()
            val lname=lastname.text.toString()
            val ag=age.text.toString()
            val practice=practitioner.text.toString()
            val rnk=rank.text.toString()
            val idd=id.text.toString()
            val ofcno=offcieno.text.toString()
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
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val index = email.indexOf('@')
                        val username: String? = if (index == -1) null else email.substring(0,index)
                        val dcinfo=docinfo(fname,lname,ag,practice,rnk,idd,ofcno)
                        val user=Usertype(username,"doctor")
                        if (username != null) {
                            db1.child(username).setValue(dcinfo)
                            db.child(username).setValue(user).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, admin::class.java))
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