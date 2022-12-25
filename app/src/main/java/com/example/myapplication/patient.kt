package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class patient : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
        var seeandselectdoctor= findViewById<CardView>(R.id.Seeandselectdoctor)
        var seemassage= findViewById<CardView>(R.id.Seemassage)
        var profile= findViewById<CardView>(R.id.profile)
        var updateprofile= findViewById<CardView>(R.id.updateprofile)
        var deleteaccount= findViewById<CardView>(R.id.Deleteaccount)
        var appointment=findViewById<CardView>(R.id.appointment)
        var logout= findViewById<CardView>(R.id.Logout)
        var blood=findViewById<CardView>(R.id.blood_bank)
        db= FirebaseDatabase.getInstance().getReference("PatientInfo")
        updateprofile.setOnClickListener{
            val intent= Intent(this, Updatepatprofile::class.java)
            startActivity(intent)
            finish()
        }
        seemassage.setOnClickListener{
            val intent= Intent(this, Patmsg::class.java)
            startActivity(intent)
            finish()
        }
        appointment.setOnClickListener{
            val intent= Intent(this, PatientAppointment::class.java)
            startActivity(intent)
            finish()
        }
        deleteaccount.setOnClickListener{
            val user=Firebase.auth.currentUser
            user?.delete()?.addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(applicationContext, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Log.e("Error",it.exception.toString())
                }
            }
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        seeandselectdoctor.setOnClickListener{
            val intent= Intent(this, Seeandselectdoc::class.java)
            startActivity(intent)
            finish()
        }
        profile.setOnClickListener{
            val intent= Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }
        blood.setOnClickListener{
            val intent= Intent(this, SelectRegistrationActivity::class.java)
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