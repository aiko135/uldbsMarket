package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.model.dto.ReqResult
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(private val chatRepository: ChatRepository): ViewModel() {
    private val _chatsLD = MutableLiveData< ReqResult<List<Chat>> >();
    val chatsLD: LiveData<ReqResult< List<Chat>> >
        get() = _chatsLD;

    private val _messagesLD = MutableLiveData< ReqResult<List<Message>> >();
    val messagesLD : LiveData< ReqResult<List<Message>> >
        get() = _messagesLD;

    //Чат создан
    private val _isChatCreatedSLD = SingleLiveEvent<ReqResult<Boolean>>();
    val isChatCreatedSLD : LiveData<ReqResult<Boolean>>
        get() = _isChatCreatedSLD;

    fun loadChats(userUuid : UUID, asManager:Boolean = false){
        viewModelScope.launch(Dispatchers.IO){
            val res = chatRepository.getChats(userUuid.toString(), asManager);
            _chatsLD.postValue(res)
        }
    }

    fun loadMessages(chatId: UUID){
        if(chatsLD.value == null || !(chatsLD.value!!.isSuccess) || chatsLD.value!!.entity == null )
            return;

        viewModelScope.launch(Dispatchers.IO){
            chatsLD.value!!.entity!!.find { it.uuid == chatId }.also {
                if(it != null){
                    val res = chatRepository.getMessagesByChat(it.uuid.toString())
                    _messagesLD.postValue(res)
                }
            }
        }
    }
    fun clearMessages(){
        _messagesLD.value = ReqResult(false,"",null)
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
            _messagesLD.postValue(res);
        }
    }
}