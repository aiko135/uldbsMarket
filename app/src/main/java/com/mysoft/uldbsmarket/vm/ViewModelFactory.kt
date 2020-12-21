package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.mysoft.uldbsmarket.network.repositories.UserRepository

class ViewModelFactory() : ViewModelProvider.Factory {
    private val retrofit = RetrofitClient.getUserRetrofitInstance()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val repository = UserRepository(retrofit)
            return LoginViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}