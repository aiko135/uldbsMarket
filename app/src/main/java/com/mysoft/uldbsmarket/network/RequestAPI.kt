package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Status
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

    @GET("/uldbs-back/request/clientRequests/{userid}")
    fun getClientRequests(@Path("userid") clientId : String): Call<List<MyRequestDto>>

    @GET("/uldbs-back/request/managerRequests/{userid}")
    fun getManagerRequests(@Path("userid") managerId : String): Call<List<MyRequestDto>>

    @GET("/uldbs-back/statushistory/getAllStatus")
    fun getAllStatus():Call<List<Status>>
}