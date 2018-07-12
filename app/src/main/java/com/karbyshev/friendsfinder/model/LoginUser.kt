package com.karbyshev.friendsfinder.model

import android.databinding.BaseObservable
import android.text.TextUtils

class LoginUser(private var name: String) : BaseObservable() {

    val isDataValid: Boolean
        get() = (!TextUtils.isEmpty(name))

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }
}