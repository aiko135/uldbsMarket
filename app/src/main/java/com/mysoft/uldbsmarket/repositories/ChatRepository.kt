package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.model.dto.RequestError
import com.mysoft.uldbsmarket.model.dto.RequestResult
import com.mysoft.uldbsmarket.model.dto.RequestSuccess
import com.mysoft.uldbsmarket.network.ChatAPI
import com.mysoft.uldbsmarket.network.MessageAPI
import retrofit2.Call
import retrofit2.Response

class ChatRepository(
    private val chatAPI : ChatAPI,
    private val messageAPI: MessageAPI,
    context: Context,
) : AbstractRepository(context) {

    override val tag: String = "ChatRepository-network";

    suspend fun getChats(userId : String, asManager:Boolean):RequestResult< List<Chat> >{
        val call : Call<List<Chat>> = if(asManager)
            chatAPI.getChatsByManager(userId)
        else
            chatAPI.getChatsByClient(userId)
        return super.executeRequest(call)
    }

    suspend fun getMessagesByChat(chatId : String):RequestResult< List<Message> >{
        val call : Call<List<Message>> = messageAPI.getMessagesForChat(chatId)
        return super.executeRequest(call)
    }

    suspend fun createChat(clientId: String, managerId:String):RequestResult<Boolean>{
        val call : Call<Boolean> = chatAPI.createChat(clientId,managerId)
        return super.executeRequest(call)
    }

    suspend fun autoCrateChat(clientId: String):RequestResult<Boolean>{
        val call : Call<Boolean> = chatAPI.autoCreateChat(clientId)
        return super.executeRequest(call)
    }

    suspend fun postMessage(clientId: String, chatId: String, text: String):RequestResult< List<Message> > {
        val call : Call<List<Message>> = messageAPI.postRequest(clientId,chatId,text)
        return super.executeRequest(call)
    }
}