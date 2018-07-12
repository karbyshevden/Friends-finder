package com.karbyshev.friendsfinder

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App : Application() {
    private val PREFS_FILENAME = "com.karbyshev.friensfinder.prefs"

    companion object {
        lateinit var preferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        preferences = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }
}