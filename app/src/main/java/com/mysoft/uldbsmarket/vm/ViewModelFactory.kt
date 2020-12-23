package com.mysoft.uldbsmarket.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.repositories.GoodRepository
import com.mysoft.uldbsmarket.repositories.MessageRepository
import com.mysoft.uldbsmarket.repositories.UserRepository

class ViewModelFactory(private val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val user_repository = UserRepository( RetrofitClient.getUserRetrofitInstance() , context)
        val chat_repository = ChatRepository( RetrofitClient.getChatRetrofitInstance())
        val message_repository = MessageRepository(RetrofitClient.getMessageRetrofitInstance())
        val good_repository = GoodRepository(RetrofitClient.getGoodRetrofitInstance());
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
            modelClass.isAssignableFrom(ChatViewModel::class.java)->{
                return ChatViewModel(message_repository) as T;
            }
            modelClass.isAssignableFrom(CatalogViewModel::class.java)->{
                return CatalogViewModel(good_repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel")
            }
        }
    }
}