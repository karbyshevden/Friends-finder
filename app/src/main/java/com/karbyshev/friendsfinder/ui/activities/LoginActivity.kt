package com.karbyshev.friendsfinder.ui.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.karbyshev.friendsfinder.App
import com.karbyshev.friendsfinder.R
import com.karbyshev.friendsfinder.databinding.ActivityLoginBinding
import com.karbyshev.friendsfinder.viewModel.LoginViewModel
import com.karbyshev.friendsfinder.viewModel.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginResultCallbacks {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val NAME: String = "NAME"
    }

    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityLoginBinding =
                DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        activityLoginBinding.viewModel = ViewModelProviders
                .of(this, LoginViewModelFactory(this))
                .get(LoginViewModel::class.java)

        checkPermission()

        name = App.preferences.getString(NAME, "")

        if (!name.isEmpty()){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSuccess() {
            name = welcomeEditText.text.toString()

            val editor = App.preferences.edit()
            editor.putString(NAME, name)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
    }

    override fun onError() {
        toast("Enter your name please!")
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
}
