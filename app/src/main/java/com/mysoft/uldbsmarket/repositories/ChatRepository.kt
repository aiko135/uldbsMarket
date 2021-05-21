package com.mysoft.uldbsmarket.repositories

import android.util.Log
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.network.ChatAPI
import com.mysoft.uldbsmarket.network.MessageAPI
import retrofit2.Call
import retrofit2.Response

class ChatRepository(private val chatAPI : ChatAPI, private val messageAPI: MessageAPI) {
    private val tag: String = "ChatRepository-network";

    suspend fun getChatsByClient(clientId : String):List<Chat>?{
        val call : Call<List<Chat>>
        var res : Response<List<Chat>>? = null;
        try {
            call = chatAPI.getChatsByClient(clientId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            return null
        }
        finally {
            if(res != null && res.isSuccessful){
                val result : List<Chat>? = res.body();
                if(result != null)
                    return result;
            }
            return null
        }
    }


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