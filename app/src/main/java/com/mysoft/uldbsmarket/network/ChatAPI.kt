package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Chat
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Header

interface ChatAPI {
    @GET("/uldbs-back/chat/getChatsByClient ")
    fun getChatsByClient(@Header("client") clientId:String):Call<List<Chat>>
}