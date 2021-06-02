package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Chat
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header

interface ChatAPI {
    @GET("/uldbs-back/chat/getChatsByClient")
    fun getChatsByClient(@Header("client") clientId:String):Call<List<Chat>>

    @GET("/uldbs-back/chat/getChatsByManager")
    fun getChatsByManager(@Header("manager") managerId:String):Call<List<Chat>>

    @GET("/uldbs-back/chat/createChat")
    fun createChat(
        @Header("client") clientId:String,
        @Header("manager") managerId:String):Call<Boolean>

    @GET("/uldbs-back/chat/autoCreateChat")
    fun autoCreateChat(@Header("client") clientId:String):Call<Boolean>
}