package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Patmsg : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var db1: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userarraylist : ArrayList<message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seedoctor)
        userRecyclerView= findViewById(R.id.userList)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userarraylist= arrayListOf<message>()
        getuserdata()
    }
    private fun getuserdata() {
        var user= FirebaseAuth.getInstance().currentUser
        var uid= user?.uid.toString()
        db1=FirebaseDatabase.getInstance().getReference("PatientInfo")
        db1.child(uid).get().addOnSuccessListener {
           if(it.exists()){
               val mobileno=it.child("mobileno").value.toString()
               db=FirebaseDatabase.getInstance().getReference(mobileno)
               db.addValueEventListener(object :ValueEventListener{
                   override fun onDataChange(snapshot: DataSnapshot) {
                       if(snapshot.exists()){
                           for(userSnapshot in snapshot.children){
                               val user=userSnapshot.getValue(message::class.java)
                               userarraylist.add(user!!)
                           }
                           userRecyclerView.adapter= Patmsgadp(userarraylist)
                       }
                   }

                   override fun onCancelled(error: DatabaseError) {
                       TODO("Not yet implemented")
                   }

               })
           }
        }
    }
}