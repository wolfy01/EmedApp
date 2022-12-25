package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Docmsg : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docmsg)
        var fn=findViewById<EditText>(R.id.fn)
        var did=findViewById<EditText>(R.id.did)
        var phone=findViewById<EditText>(R.id.phone)
        var msg=findViewById<EditText>(R.id.msg)
        var btn=findViewById<Button>(R.id.btn_submit)
        btn.setOnClickListener {
            var phone=phone.text.toString()
            val fn=fn.text.toString()
            val did=did.text.toString()
            val msg=msg.text.toString()
            val mg="From DR. $fn ID: $did \n$msg"
            var db= FirebaseDatabase.getInstance().getReference(phone)
            val mg1=message(mg)
            db.child(fn).setValue(mg1).addOnSuccessListener {
                Toast.makeText(applicationContext, "Message sent.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Doctor::class.java))
                finish()
            }
        }
    }
}