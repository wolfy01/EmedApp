package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ApprovalAdp(private val userList : ArrayList<DocNpatinfo>) : RecyclerView.Adapter<ApprovalAdp.MyViewHolder>() {
    private lateinit var mlistener: onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position: Int)
    }
    fun setonItemClickListener(listner: onItemClickListner){
        mlistener=listner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.approval_list,
            parent,false)
        return MyViewHolder(itemView,mlistener)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.firstName.text = currentitem.firstname
        holder.lastName.text = currentitem.lastname
        holder.id.text=currentitem.id
        holder.pid.text=currentitem.uid
    }
    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(itemView : View,listner: ApprovalAdp.onItemClickListner) : RecyclerView.ViewHolder(itemView){
        val firstName : TextView = itemView.findViewById(R.id.firstName)
        val lastName : TextView = itemView.findViewById(R.id.lastName)
        val id:TextView=itemView.findViewById(R.id.id)
        val pid:TextView=itemView.findViewById(R.id.pid)
        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }

    }

}