package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SelectRegistrationActivity : AppCompatActivity() {
    private lateinit var donorRegisterButton: Button
    private lateinit var recipientRegisterButton: Button
    private lateinit var loginButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_registration)

        donorRegisterButton = findViewById(R.id.select_donor_register_button)
        recipientRegisterButton = findViewById(R.id.select_recipient_register_button)

        donorRegisterButton.setOnClickListener {
            val intent = Intent(this@SelectRegistrationActivity, DonorRegistrationActivity::class.java)
            startActivity(intent)
        }

        recipientRegisterButton.setOnClickListener {
            val intent = Intent(this@SelectRegistrationActivity, RecipientRegistrationActivity::class.java)
            startActivity(intent)
        }

    }
}