package com.karbyshev.friendsfinder.viewModel

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karbyshev.friendsfinder.data.FirebaseLiveData
import com.karbyshev.friendsfinder.model.User

class MainViewModel : ViewModel() {
    companion object {
        private var users = ArrayList<User>()
    }


    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun getUsers(): LiveData<List<User>> {

        val mLiveData = FirebaseLiveData(databaseReference)

        return Transformations.map(mLiveData, Function<DataSnapshot, List<User>> {
            users.clear()
            it.children.forEach { it.getValue(User::class.java)!!.let { users.add(it) } }
            return@Function users
        })
    }
}