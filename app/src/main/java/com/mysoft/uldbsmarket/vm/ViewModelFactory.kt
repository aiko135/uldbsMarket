package com.mysoft.uldbsmarket.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mysoft.uldbsmarket.network.*
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.repositories.GoodRepository
import com.mysoft.uldbsmarket.repositories.UserRepository

class ViewModelFactory(private val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val user_repository = UserRepository( RetrofitClient.getTypedRetrofitInstance(UserAPI::class.java) , context)
        val chat_repository = ChatRepository(
            RetrofitClient.getTypedRetrofitInstance(ChatAPI::class.java),
            RetrofitClient.getTypedRetrofitInstance(MessageAPI::class.java)
        )
        val good_repository = GoodRepository(RetrofitClient.getTypedRetrofitInstance(GoodAPI::class.java), context);
        when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                return UserViewModel(user_repository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java)->{
                return ChatViewModel(chat_repository, user_repository) as T;
            }
            modelClass.isAssignableFrom(GoodViewModel::class.java)->{
                return GoodViewModel(good_repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel")
            }
        }
    }
}