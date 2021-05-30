package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.dto.FullGoodInfoDto
import com.mysoft.uldbsmarket.model.dto.MyRequestDto
import com.mysoft.uldbsmarket.model.dto.UsersRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RequestAPI {
    @POST("/uldbs-back/request/postRequest")
    fun postRequest(@Body usersRequest: UsersRequestDto):Call<Boolean>

    @GET("/uldbs-back/request/myRequests/{userid}")
    fun getMyRequests(@Path("userid") goodUuid : String): Call<List<MyRequestDto>>
}