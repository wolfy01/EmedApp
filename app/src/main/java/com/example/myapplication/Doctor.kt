package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Doctor : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        var logout= findViewById<CardView>(R.id.logout)
        var approval=findViewById<CardView>(R.id.approval)
        var seepatientlist=findViewById<CardView>(R.id.patientlist)
        var bloodbank=findViewById<CardView>(R.id.bloodbank)
        var clearday=findViewById<CardView>(R.id.clearday)
        var sendmsg=findViewById<CardView>(R.id.notes)
        bloodbank.setOnClickListener{
            val intent= Intent(this, SelectRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
        sendmsg.setOnClickListener{
            val intent= Intent(this, Docmsg::class.java)
            startActivity(intent)
            finish()
        }
        clearday.setOnClickListener{
            var user= FirebaseAuth.getInstance().currentUser
            var email= user?.email.toString()
            val index = email.indexOf('@')
            val username: String? = if (index == -1) null else email.substring(0,index)
            var db= FirebaseDatabase.getInstance().getReference(username!!).removeValue()
            Toast.makeText(applicationContext, "Reset successful", Toast.LENGTH_SHORT).show()
        }
        approval.setOnClickListener{
            val intent= Intent(this, Approval::class.java)
            startActivity(intent)
            finish()
        }
        seepatientlist.setOnClickListener{
            val intent= Intent(this, Patientlist::class.java)
            startActivity(intent)
            finish()
        }
        logout.setOnClickListener{
            Toast.makeText(applicationContext, "Logout successful", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}