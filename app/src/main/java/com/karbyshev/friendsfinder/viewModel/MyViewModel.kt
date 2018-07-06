package com.karbyshev.friendsfinder.viewModel

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karbyshev.friendsfinder.data.FirebaseQueryLiveData
import com.karbyshev.friendsfinder.model.User

class MyViewModel : ViewModel() {
companion object {
    private var users = ArrayList<User>()
}

    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun getUsers() : LiveData<List<User>> {

        var mLiveData = FirebaseQueryLiveData(databaseReference)

        var mUserLiveData: LiveData<List<User>> = Transformations.map(mLiveData, Deserializer())

        return mUserLiveData
    }

    private class Deserializer : Function<DataSnapshot, List<User>> {
        override fun apply(input: DataSnapshot?): List<User> {
            users.clear()
            for (user in input!!.children) {
                var newUser = user.getValue(User::class.java)
                users.add(newUser!!)
            }
            return users
        }
    }
}