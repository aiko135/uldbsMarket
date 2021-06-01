package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.model.dto.UsersRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MessageAPI {
    @GET("/uldbs-back/message/getMessagesForChat")
    fun getMessagesForChat(@Header("chat") chatId:String): Call<List<Message>>

    @POST("/uldbs-back/message/postMessage")
    fun postRequest(
        @Header("userid") userId:String,
        @Header("chatid") chatId:String,
        @Body text:String
    ):Call<List<Message>>
}