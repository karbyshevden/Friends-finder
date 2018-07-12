package com.karbyshev.friendsfinder.ui.fragments.list.adapter

import android.support.v7.widget.RecyclerView
import com.karbyshev.friendsfinder.databinding.UserBinding
import com.karbyshev.friendsfinder.model.User

class ViewHolder(private val binding: UserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User) {
        binding.item = item
        binding.executePendingBindings()
    }
}
