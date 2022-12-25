package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blood_layout, parent, false)
        context = parent.context
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = users[position]

        holder.blood.text = item.blood
        holder.name.text = item.name
        holder.phone.text = item.phone

        holder.view.setOnClickListener {
            val intent = Intent(context, BloodDetail::class.java)
            intent.putExtra("uid", item.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var view : LinearLayout = itemView.findViewById(R.id.blood_view)
        val name : TextView = itemView.findViewById(R.id.blood_view_name)
        val blood : TextView = itemView.findViewById(R.id.blood_view_group)
        val phone : TextView = itemView.findViewById(R.id.blood_view_phone)
    }
}