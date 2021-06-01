package com.mysoft.uldbsmarket.vm

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.ReqResult
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.repositories.UserRepository
import com.mysoft.uldbsmarket.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ChatViewModel(private val chatRepository: ChatRepository): ViewModel() {
    private val _chats = MutableLiveData< ReqResult<List<Chat>> >();
    val chats: LiveData<ReqResult< List<Chat>> >
        get() = _chats;

    private val _messages = MutableLiveData< ReqResult<List<Message>> >();
    val messages : LiveData< ReqResult<List<Message>> >
        get() = _messages;

    //Чат создан
    private val _isChatCreatedSLD = SingleLiveEvent<ReqResult<Boolean>>();
    val isChatCreatedSLD : LiveData<ReqResult<Boolean>>
        get() = _isChatCreatedSLD;

    fun loadChats(userUuid : UUID){
        viewModelScope.launch(Dispatchers.IO){
            val res = chatRepository.getChatsByClient(userUuid.toString());
            _chats.postValue(res)
        }
    }

    fun loadMessages(chatId: UUID){
        if(chats.value == null || !(chats.value!!.isSuccess) || chats.value!!.entity == null )
            return;

        viewModelScope.launch(Dispatchers.IO){
            chats.value!!.entity!!.find { it.uuid == chatId }.also {
                if(it != null){
                    val res = chatRepository.getMessagesByChat(it.uuid.toString())
                    _messages.postValue(res)
                }
            }
        }
    }


    fun createChat(clientUuid:UUID, managerUuid:UUID){
        viewModelScope.launch(Dispatchers.IO){
            val res = chatRepository.createChat(clientUuid.toString(),managerUuid.toString());
            _isChatCreatedSLD.postValue(res);
        }
    }

    fun autoCreateChat(clientUuid: UUID){
        viewModelScope.launch(Dispatchers.IO){
            val res = chatRepository.autoCrateChat(clientUuid.toString());
            _isChatCreatedSLD.postValue(res);
        }
    }

    fun postMessage(userId: String, chatId: String, text:String){
        viewModelScope.launch(Dispatchers.IO){
            val text_formatted = text.replace("\n", " ")
            val res = chatRepository.postMessage(userId, chatId, text_formatted)
            _messages.postValue(res);
        }
    }
}