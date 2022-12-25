package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.view.animation.Animation
import android.os.Bundle
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {
    private var logo: ImageView? = null
    private var title: TextView? = null
    private var slogan: TextView? = null
    private var topAnimation: Animation? = null
    private var bottomAnimation: Animation? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = Firebase.auth
        logo = findViewById(R.id.logo)
        title = findViewById(R.id.title)
        slogan = findViewById(R.id.slogan)
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            val loginIntent = Intent(this@SplashScreenActivity, Docmsg::class.java)
            startActivity(loginIntent)
            finish()
        } else {
            val db = Firebase.database.getReference("Users")
            db.child(auth.currentUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val type = dataSnapshot.child("type").getValue<String>()
                    val blood = dataSnapshot.child("blood").getValue<String>()

                    val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    mainIntent.putExtra("type", type)
                    mainIntent.putExtra("blood", blood)
                    startActivity(mainIntent)
                    finish()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        }
    }
}