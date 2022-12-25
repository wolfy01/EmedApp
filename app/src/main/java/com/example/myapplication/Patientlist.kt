package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Patientlist : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userarraylist : ArrayList<Appointmentinfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seedoctor)
        userRecyclerView= findViewById(R.id.userList)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userarraylist= arrayListOf<Appointmentinfo>()
        getuserdata()
    }
    private fun getuserdata() {
        var user= FirebaseAuth.getInstance().currentUser
        var email= user?.email.toString()
        val index = email.indexOf('@')
        val username: String? = if (index == -1) null else email.substring(0,index)
        db= FirebaseDatabase.getInstance().getReference(username!!)
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user1=userSnapshot.getValue(Appointmentinfo::class.java)
                        userarraylist.add(user1!!)
                    }
                    userRecyclerView.adapter= Patientlistadp(userarraylist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}