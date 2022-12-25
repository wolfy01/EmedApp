package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Setappointment : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var db1: DatabaseReference
    private lateinit var db2: DatabaseReference
    private lateinit var db3: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setappointment)
        val bundle:Bundle?=intent.extras
        val id=bundle!!.getString("id").toString()
        val pid=bundle!!.getString("pid").toString()
        var time=findViewById<EditText>(R.id.time1)
        var date=findViewById<EditText>(R.id.date1)
        var fn=findViewById<EditText>(R.id.fn)
        var did=findViewById<EditText>(R.id.did)
        var btn=findViewById<Button>(R.id.btn_submit)
        var user= FirebaseAuth.getInstance().currentUser
        var email= user?.email.toString()
        val index = email.indexOf('@')
        val username: String? = if (index == -1) null else email.substring(0,index)
        var db= FirebaseDatabase.getInstance().getReference(username!!)
        var db2= FirebaseDatabase.getInstance().getReference(pid)
        var db1= FirebaseDatabase.getInstance().getReference("PatientInfo")
        btn.setOnClickListener {
            db1.child(pid).get().addOnSuccessListener { it1 ->
                if(it1.exists()){
                    val fname=it1.child("firstname").value.toString()
                    val lname=it1.child("lastname").value.toString()
                    val age=it1.child("age").value.toString()
                    val mobileno=it1.child("mobileno").value.toString()
                    val bloodgrp=it1.child("bloodgroup").value.toString()
                    val address=it1.child("addres").value.toString()
                    val time=time.text.toString()
                    val date=date.text.toString()
                    val fn=fn.text.toString()
                    val did=did.text.toString()
                    val userin=Appointmentinfo(fname,lname,age,mobileno,bloodgrp,address,time,date)
                    var mg="Your appointment with Dr. $fn ID: $did has been set. Time: $time Date: $date"
                    val msg=Messageinfo(username,time, date,mg)
                    if (username != null) {
                        db.child(pid).setValue(userin).addOnSuccessListener {
                            db2.child(username!!).setValue(msg).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Patient has been notified of appointment", Toast.LENGTH_SHORT).show()
                            }
                            Toast.makeText(applicationContext, "Appointment has been set", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Doctor::class.java))
                            finish()
                        }
                    }
                }
            }
        }

    }
}