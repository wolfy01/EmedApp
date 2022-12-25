package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Profile : AppCompatActivity() {
    private lateinit var user: FirebaseUser
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        db= FirebaseDatabase.getInstance().getReference("PatientInfo")
        var user= FirebaseAuth.getInstance().currentUser
        var uid= user?.uid.toString()
        var fullname=findViewById<TextView>(R.id.name)
        var age=findViewById<TextView>(R.id.age)
        var mobile=findViewById<TextView>(R.id.mobile)
        var bloodgrp=findViewById<TextView>(R.id.bloodgrp)
        var address=findViewById<TextView>(R.id.address)
        db.child(uid).get().addOnSuccessListener {
            if(it.exists()){
                val fname1=it.child("firstname").value.toString()
                val lname1=it.child("lastname").value.toString()
                val age1 =it.child("age").value.toString()
                val mobile1=it.child("mobileno").value.toString()
                val bloodgrp1=it.child("bloodgroup").value.toString()
                val address1=it.child("addres").value.toString()
                fullname.setText(fname1+" "+lname1)
                age.setText(age1)
                mobile.setText(mobile1)
                bloodgrp.setText(bloodgrp1)
                address.setText(address1)
            }
        }
    }
}