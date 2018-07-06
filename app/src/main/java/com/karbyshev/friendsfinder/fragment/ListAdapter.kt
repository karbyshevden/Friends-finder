package com.karbyshev.friendsfinder.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.karbyshev.friendsfinder.R
import com.karbyshev.friendsfinder.model.User

class ListAdapter (private val userList: List<User>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layuot, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = userList[position].name
        holder.lat?.text = "Lat: " + userList[position].lat
        holder.lng?.text = "Lng: " + userList[position].lng
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val name = itemView?.findViewById<TextView>(R.id.itemNameTextView)
        val lat = itemView?.findViewById<TextView>(R.id.itemLatTextView)
        val lng = itemView?.findViewById<TextView>(R.id.itemLngTextView)
    }
}