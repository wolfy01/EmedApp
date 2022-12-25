package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DonorRegistrationActivity : AppCompatActivity() {
    private lateinit var donorFullName: TextInputEditText
    private lateinit var donorIdNumber: TextInputEditText
    private lateinit var donorPhoneNumber: TextInputEditText
    private lateinit var donorBloodGroup: Spinner
    private lateinit var donorEmail: TextInputEditText
    private lateinit var donorRegisterButton: Button
    private lateinit var loadingBar: ProgressDialog
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_registration)
        donorFullName = findViewById(R.id.donor_register_full_name)
        donorIdNumber = findViewById(R.id.donor_register_id_number)
        donorPhoneNumber = findViewById(R.id.donor_register_phone_number)
        donorBloodGroup = findViewById(R.id.donor_register_blood_group)
        donorEmail = findViewById(R.id.donor_register_email)
        donorRegisterButton = findViewById(R.id.donor_register_button)


        loadingBar = ProgressDialog(this)
        val bloodGroups = arrayOf("Select your blood group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        var selectedBloodGroup = bloodGroups.first()

        val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, bloodGroups )
        donorBloodGroup.adapter = adapter

        donorBloodGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedBloodGroup = bloodGroups[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //...
            }
        }
        donorRegisterButton.setOnClickListener {
            registerNewDonor(selectedBloodGroup)
        }
    }
    private fun registerNewDonor(bloodGroup: String) {
        val name = donorFullName.text.toString()
        val nid = donorIdNumber.text.toString()
        val phone = donorPhoneNumber.text.toString()
        val email = donorEmail.text.toString()
        val bloodgroup=bloodGroup.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, "Name Required", Toast.LENGTH_SHORT).show()
        } else if (nid.isEmpty()) {
            Toast.makeText(this, "ID Required", Toast.LENGTH_SHORT).show()
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "Phone Required", Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Email Required", Toast.LENGTH_SHORT).show()
        }  else if (bloodGroup.isEmpty() || bloodGroup == "Select your blood group") {
            Toast.makeText(this, "Blood Group Required", Toast.LENGTH_SHORT).show()
        } else {
            var db= FirebaseDatabase.getInstance().getReference("donars")
            val donar=donarinfo(name,phone, nid, email, bloodgroup)
            db.child(nid).setValue(donar).addOnSuccessListener {
                Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show()
                val intent= Intent(this, Doctor::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
}