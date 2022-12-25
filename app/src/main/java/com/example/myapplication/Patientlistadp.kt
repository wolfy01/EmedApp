package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Patientlistadp(private val userList : ArrayList<Appointmentinfo>) : RecyclerView.Adapter<Patientlistadp.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.patient_list,
            parent,false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.firstName.text = currentitem.firstname
        holder.lastName.text = currentitem.lastname
        holder.age.text = currentitem.age
        holder.address.text = currentitem.addres
        holder.mobileno.text = currentitem.mobileno
        holder.bloodgroup.text = currentitem.bloodgroup
        holder.time.text=currentitem.time
        holder.date.text=currentitem.date
    }
    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val firstName : TextView = itemView.findViewById(R.id.firstName)
        val lastName : TextView = itemView.findViewById(R.id.lastName)
        val age : TextView = itemView.findViewById(R.id.age)
        val address : TextView = itemView.findViewById(R.id.address)
        val mobileno : TextView = itemView.findViewById(R.id.mobileno)
        val bloodgroup : TextView = itemView.findViewById(R.id.bloodgroup)
        val time: TextView = itemView.findViewById(R.id.time)
        val date: TextView = itemView.findViewById(R.id.date)

    }

}