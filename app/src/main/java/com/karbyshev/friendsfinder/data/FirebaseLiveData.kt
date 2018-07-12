package com.karbyshev.friendsfinder.data

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class FirebaseLiveData : LiveData<DataSnapshot> {

    private var ref: DatabaseReference

    constructor(ref: DatabaseReference) : super() {
        this.ref = ref
    }

    override fun onActive() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                value = dataSnapshot
            }
        })
    }
}