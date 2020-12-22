package com.mysoft.uldbsmarket.vm

import androidx.datastore.preferences.core.toMutablePreferences
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {

    fun register(u: User, onFinish:(RegisterResult)->Unit, onError:()->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            var result : RegisterResult?= userRepository.register(u);
            if(result == null)
                onError.invoke();
            else
                onFinish.invoke(result)
        }
    }
}