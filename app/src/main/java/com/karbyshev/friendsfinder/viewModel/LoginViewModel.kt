package com.karbyshev.friendsfinder.viewModel

import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.karbyshev.friendsfinder.model.LoginUser
import com.karbyshev.friendsfinder.ui.activities.LoginResultCallbacks

class LoginViewModel(private val listener: LoginResultCallbacks) : ViewModel() {

    private val loginUser: LoginUser = LoginUser("")

    val nameTextWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginUser.setName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }

    fun onLoginClicked(v: View) {
        if (loginUser.isDataValid) {
            listener.onSuccess()
        } else {
            listener.onError()
        }
    }
}