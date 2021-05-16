package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatsViewModel(private val chatRepository: ChatRepository,
                     private val userRepository: UserRepository): ViewModel() {

    private val _chats = MutableLiveData<List<Chat>>();
    val chats: LiveData<List<Chat>>
        get() = _chats;

    private val _user = MutableLiveData<User?>();
    val user: LiveData<User?>
        get() = _user;

    fun loadChats(userUuid : String, onError:()->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            var result : List<Chat>?= chatRepository.getChatsByClient(userUuid);
            if(result == null)
                onError.invoke();
            else
               _chats.postValue(result);
        }
    }

    fun readUserInfo( ){
        CoroutineScope(Dispatchers.IO).launch {
            val found : User? = userRepository.readUserPref_sync()
            withContext(Dispatchers.Main) {
                _user.postValue(found);
            }
        }
    }
}