package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.repositories.ChatRepository
import com.mysoft.uldbsmarket.repositories.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(private val messageRepository: MessageRepository): ViewModel() {
    private val _messages = MutableLiveData<List<Message>>();
    val messages : LiveData<List<Message>>
        get() = _messages;

    public var chatid : String? = null
    public var userid : String? = null;

    fun loadMessages(onError:()->Unit){
        if(chatid == null)
            onError.invoke();

        CoroutineScope(Dispatchers.IO).launch {
            var result : List<Message>? = messageRepository.getMessagesByChat(chatid!!);
            if(result == null)
                onError.invoke();
            else
                _messages.postValue(result);
        }
    }
}