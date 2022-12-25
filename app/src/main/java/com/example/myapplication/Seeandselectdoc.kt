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

class Seeandselectdoc : AppCompatActivity() {
    private lateinit var user: FirebaseUser
    private lateinit var db: DatabaseReference
    private lateinit var db1: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userarraylist : ArrayList<docinfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seedoctor)
        userRecyclerView= findViewById(R.id.userList)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userarraylist= arrayListOf<docinfo>()
        db1=FirebaseDatabase.getInstance().getReference("DoctorNpatient")
        var user= FirebaseAuth.getInstance().currentUser
        var uid= user?.uid.toString()
        getuserdata()
    }
    private fun getuserdata() {
        db= FirebaseDatabase.getInstance().getReference("DoctorInfo")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user=userSnapshot.getValue(docinfo::class.java)
                        userarraylist.add(user!!)
                    }
                    var adapter1=Seedocadp(userarraylist)
                    userRecyclerView.adapter= adapter1
                    adapter1.setonItemClickListener(object : Seedocadp.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val fname=userarraylist[position].firstname.toString()
                            val lname=userarraylist[position].lastname.toString()
                            val id=userarraylist[position].id.toString()
                            var user= FirebaseAuth.getInstance().currentUser
                            var uid= user?.uid.toString()
                            val user1=SeeSeldoc(fname,lname,id,uid)
                            db1.child(uid).setValue(user1).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Selection successful please wait for appointment approval", Toast.LENGTH_SHORT).show()
                                val intent= Intent(this@Seeandselectdoc,patient::class.java)
                                startActivity(intent)
                            }
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