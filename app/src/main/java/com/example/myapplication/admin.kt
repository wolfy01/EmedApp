package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView

class admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        var action=supportActionBar
        action!!.title="Admin panel"
        var adddoctor= findViewById<CardView>(R.id.adddoctor)
        var deldoctor=findViewById<CardView>(R.id.removedoctor)
        var seedoctor=findViewById<CardView>(R.id.seedoctor)
        var seepatient=findViewById<CardView>(R.id.seepatient)
        var addadmin=findViewById<CardView>(R.id.addadmin)
        var logout=findViewById<CardView>(R.id.logout)
        logout.setOnClickListener{
            Toast.makeText(applicationContext, "Logout successful", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        seepatient.setOnClickListener{
            val intent= Intent(this, SeepatAdmin::class.java)
            startActivity(intent)
            finish()
        }
        addadmin.setOnClickListener{
            val intent= Intent(this, AddinAdmin::class.java)
            startActivity(intent)
            finish()
        }
        adddoctor.setOnClickListener{
            val intent= Intent(this, AddinDoctor::class.java)
            startActivity(intent)
            finish()
        }
        seedoctor.setOnClickListener{
            val intent= Intent(this, Seedoctor::class.java)
            startActivity(intent)
            finish()
        }
        deldoctor.setOnClickListener{
            val intent= Intent(this, Deletedoctor::class.java)
            startActivity(intent)
            finish()
        }
    }
}