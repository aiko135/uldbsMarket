package com.mysoft.uldbsmarket.repositories

import android.util.Log
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.network.MessageAPI
import retrofit2.Call
import retrofit2.Response

class MessageRepository(private val messageAPI: MessageAPI) {
    private val tag: String = "MessageRepository-network";

    suspend fun getMessagesByChat(chatId : String):List<Message>?{
        val call : Call<List<Message>>
        var res : Response<List<Message>>? = null;
        try {
            call = messageAPI.getChatsByClient(chatId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            return null
        }
        finally {
            if(res != null && res.isSuccessful){
                val result : List<Message>? = res.body();
                if(result != null)
                    return result;
            }
            return null
        }
    }
}