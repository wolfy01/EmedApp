package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Updatepatprofile : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var user:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatepatprofile)
        var fname=findViewById<EditText>(R.id.first_name)
        var lname=findViewById<EditText>(R.id.last_name)
        var age=findViewById<EditText>(R.id.age)
        var mobileno=findViewById<EditText>(R.id.mobileno)
        var bloodgroup=findViewById<EditText>(R.id.bloodgroup)
        var addres=findViewById<EditText>(R.id.et_address)
        var btn=findViewById<Button>(R.id.btn_submit)
        var user= FirebaseAuth.getInstance().currentUser
        var uid= user?.uid.toString()
        var db= FirebaseDatabase.getInstance().getReference("PatientInfo")
        btn.setOnClickListener {
            val fname=fname.text.toString()
            val lname=lname.text.toString()
            val age=age.text.toString()
            val mobileno=mobileno.text.toString()
            val bloodgroup=bloodgroup.text.toString()
            val addres=addres.text.toString()
            val uid=uid
            val patinfo=Patinfo(fname,lname,age,mobileno,bloodgroup,addres,uid)
            if (uid != null) {
                db.child(uid).setValue(patinfo).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Profile updated!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, patient::class.java))
                    finish()
                }
            }

        }

    }
}