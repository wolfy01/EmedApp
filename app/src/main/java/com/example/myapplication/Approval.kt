package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Approval : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userarraylist : ArrayList<DocNpatinfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seedoctor)
        userRecyclerView= findViewById(R.id.userList)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userarraylist= arrayListOf<DocNpatinfo>()
        db= FirebaseDatabase.getInstance().getReference("DoctorNpatient")
        getuserdata()
    }
    private fun getuserdata() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user=userSnapshot.getValue(DocNpatinfo::class.java)
                        userarraylist.add(user!!)
                    }
                    var adapter1=ApprovalAdp(userarraylist)
                    userRecyclerView.adapter= adapter1
                    adapter1.setonItemClickListener(object : ApprovalAdp.onItemClickListner{
                        override fun onItemClick(position: Int) {
                           val intent=Intent(this@Approval,Setappointment::class.java)
                            intent.putExtra("id",userarraylist[position].id)
                            intent.putExtra("pid",userarraylist[position].uid)
                            db.child(userarraylist[position].uid.toString()).removeValue()
                            startActivity(intent)
                            finish()
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}