package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BloodBank : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_bank)
        var action=supportActionBar
        action!!.title="Blood Bank"
    }
}