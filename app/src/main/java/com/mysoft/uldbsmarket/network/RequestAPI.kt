package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.dto.UsersRequest
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestAPI {
    @POST("/uldbs-back/request/postRequest")
    fun postRequest(@Body usersRequest: UsersRequest):Call<Boolean>
}