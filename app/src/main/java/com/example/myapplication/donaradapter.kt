package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class donaradapter(private val userList : ArrayList<donarinfo>) : RecyclerView.Adapter<donaradapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_donar,
            parent,false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.name.text = currentitem.name
        holder.phone.text = currentitem.phone
        holder.nid.text = currentitem.nid
        holder.email.text = currentitem.email
        holder.bloodgroup.text = currentitem.bloodgroup
    }
    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.name)
        val phone : TextView = itemView.findViewById(R.id.phone)
        val nid : TextView = itemView.findViewById(R.id.nid)
        val email : TextView = itemView.findViewById(R.id.email)
        val bloodgroup : TextView = itemView.findViewById(R.id.bloodgroup)
    }

}