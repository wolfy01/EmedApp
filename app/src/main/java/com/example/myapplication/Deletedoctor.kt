package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Deletedoctor : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var db1: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userarraylist : ArrayList<deldocinfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletedoctor)
        userRecyclerView= findViewById(R.id.userList1)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userarraylist= arrayListOf<deldocinfo>()
        db1=FirebaseDatabase.getInstance().getReference("Blacklist")
        getuserdata()
    }
    private fun getuserdata() {
        db= FirebaseDatabase.getInstance().getReference("DoctorInfo")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user=userSnapshot.getValue(deldocinfo::class.java)
                        userarraylist.add(user!!)
                    }
                    var adapter1=Docdeladp(userarraylist)
                    userRecyclerView.adapter= adapter1
                    adapter1.setonItemClickListener(object : Docdeladp.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val fname=userarraylist[position].firstname.toString()
                            val lname=userarraylist[position].lastname.toString()
                            val id=userarraylist[position].id.toString()
                            val user=deldocinfo(fname,lname,id)
                            db1.child(fname).setValue(user).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Account deleted successfully!", Toast.LENGTH_SHORT).show()
                                val intent= Intent(this@Deletedoctor,admin::class.java)
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