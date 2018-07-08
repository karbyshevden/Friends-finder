package com.karbyshev.friendsfinder.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.karbyshev.friendsfinder.databinding.UserBinding
import com.karbyshev.friendsfinder.model.User

class ListAdapter(private val userList: List<User>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(userList[position])

    class ViewHolder(private val binding: UserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}