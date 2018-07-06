package com.karbyshev.friendsfinder.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.karbyshev.friendsfinder.R
import com.karbyshev.friendsfinder.R.id.welcomeButton
import com.karbyshev.friendsfinder.R.id.welcomeEditText
import com.karbyshev.friendsfinder.util.Constants
import com.karbyshev.friendsfinder.util.Constants.Companion.NAME
import com.karbyshev.friendsfinder.util.Constants.Companion.PREFS_FILENAME
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.toast

class WelcomeActivity : AppCompatActivity() {

    private lateinit var name: String
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        checkPermission()

        pref = this.getSharedPreferences(PREFS_FILENAME, 0)
        name = pref!!.getString(NAME, "root")

        if (name.equals("root")) {

            welcomeButton.setOnClickListener {
                name = welcomeEditText.text.toString()

                val editor = pref!!.edit()
                editor.putString(NAME, name)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                if (TextUtils.isEmpty(name)) {
                    toast("You need enter name!")
                } else {
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), Constants.LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
}
