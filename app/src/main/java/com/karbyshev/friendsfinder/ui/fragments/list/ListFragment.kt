package com.karbyshev.friendsfinder.ui.fragments.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karbyshev.friendsfinder.R
import com.karbyshev.friendsfinder.ui.fragments.list.adapter.ListAdapter
import com.karbyshev.friendsfinder.viewModel.MainViewModel

class ListFragment : Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.listRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        var model: MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        model.getUsers().observe(this, Observer { newUsers ->
            var adapter = ListAdapter(newUsers!!)
            recyclerView.adapter = adapter
        })
    }
}