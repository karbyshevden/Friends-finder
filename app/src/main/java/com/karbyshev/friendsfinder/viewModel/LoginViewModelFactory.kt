package com.karbyshev.friendsfinder.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karbyshev.friendsfinder.ui.activities.LoginResultCallbacks

class LoginViewModelFactory(private val listener: LoginResultCallbacks) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(listener) as T
    }
}