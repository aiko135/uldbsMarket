package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Message
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MessageAPI {
    @GET("/uldbs-back/message/getMessagesForChat")
    fun getChatsByClient(@Header("chat") chatId:String): Call<List<Message>>
}