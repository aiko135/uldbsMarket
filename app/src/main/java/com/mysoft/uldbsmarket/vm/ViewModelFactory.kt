package com.mysoft.uldbsmarket.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.repositories.UserRepository

class ViewModelFactory(private val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val user_repository = UserRepository( RetrofitClient.getUserRetrofitInstance() , context)
        val chat_repository = ChatRepository( RetrofitClient.getChatRetrofitInstance())
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(user_repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> {
                return ProfileViewModel(user_repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java)->{
                return RegisterViewModel(user_repository) as T
            }
            modelClass.isAssignableFrom(ChatsViewModel::class.java)->{
                return ChatsViewModel(chat_repository, user_repository) as T;
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel")
            }
        }
    }
}