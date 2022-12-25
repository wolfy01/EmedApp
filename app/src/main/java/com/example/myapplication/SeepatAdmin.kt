package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class SeepatAdmin : AppCompatActivity(){
    private lateinit var db: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userarraylist : ArrayList<Patinfoad>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seedoctor)
        userRecyclerView= findViewById(R.id.userList)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userarraylist= arrayListOf<Patinfoad>()
        getuserdata()
    }
    private fun getuserdata() {
        db= FirebaseDatabase.getInstance().getReference("PatientInfo")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user=userSnapshot.getValue(Patinfoad::class.java)
                        userarraylist.add(user!!)
                    }
                    userRecyclerView.adapter= Seepatadmadp(userarraylist)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}