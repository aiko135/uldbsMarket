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
    private val context: Context,
) {
    private val tag: String = "ChatRepository-network";

    suspend fun getChats(userId : String, asManager:Boolean):RequestResult< List<Chat> >{
        val call : Call<List<Chat>>
        var res : Response<List<Chat>>? = null;
        try {
            call = if(asManager)
                chatAPI.getChatsByManager(userId)
            else
                chatAPI.getChatsByClient(userId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }


    suspend fun getMessagesByChat(chatId : String):RequestResult< List<Message> >{
        val call : Call<List<Message>>
        var res : Response<List<Message>>? = null;
        try {
            call = messageAPI.getMessagesForChat(chatId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }

    suspend fun createChat(clientId: String, managerId:String):RequestResult<Boolean>{
        val call : Call<Boolean>
        var res : Response<Boolean>? = null;
        try {
            call = chatAPI.createChat(clientId,managerId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }

    suspend fun autoCrateChat(clientId: String):RequestResult<Boolean>{
        val call : Call<Boolean>
        var res : Response<Boolean>? = null;
        try {
            call = chatAPI.autoCreateChat(clientId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }

    suspend fun postMessage(clientId: String, chatId: String, text: String):RequestResult< List<Message> > {
        val call : Call<List<Message>>
        var res : Response<List<Message>>? = null;
        try {
            call = messageAPI.postRequest(clientId,chatId,text);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }
}