package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class BloodDetail : AppCompatActivity() {
    private lateinit var blood: TextView
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var nid: TextView
    private lateinit var phone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_detail)

        blood = findViewById(R.id.blood_detail_blood)
        name = findViewById(R.id.blood_detail_name)
        email = findViewById(R.id.blood_detail_email)
        nid = findViewById(R.id.blood_detail_nid)
        phone = findViewById(R.id.blood_detail_phone)

        val uid: String = intent.getStringExtra("uid").toString()
        val db = Firebase.database.getReference("Users")
        db.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    blood.text = dataSnapshot.child("blood").getValue<String>()
                    name.text = dataSnapshot.child("name").getValue<String>()
                    email.text = dataSnapshot.child("email").getValue<String>()
                    nid.text = dataSnapshot.child("nid").getValue<String>()
                    phone.text = dataSnapshot.child("phone").getValue<String>()
                } else {
                    Toast.makeText(this@BloodDetail, "Something Wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }
}