package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class docadapter(private val userList : ArrayList<docinfo>) : RecyclerView.Adapter<docadapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_doc,
            parent,false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.firstName.text = currentitem.firstname
        holder.lastName.text = currentitem.lastname
        holder.age.text = currentitem.age
        holder.practitioner.text = currentitem.practitioner
        holder.rank.text = currentitem.rank
        holder.id.text = currentitem.id
        holder.officeno.text=currentitem.officeno
    }
    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val firstName : TextView = itemView.findViewById(R.id.firstName)
        val lastName : TextView = itemView.findViewById(R.id.lastName)
        val age : TextView = itemView.findViewById(R.id.age)
        val practitioner : TextView = itemView.findViewById(R.id.practitioner)
        val rank : TextView = itemView.findViewById(R.id.rank)
        val id : TextView = itemView.findViewById(R.id.id)
        val officeno: TextView=itemView.findViewById(R.id.officeno)

    }

}