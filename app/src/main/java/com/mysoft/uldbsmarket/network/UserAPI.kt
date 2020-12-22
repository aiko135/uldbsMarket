package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPI {
    @POST("uldbs-back/user/doLogin ")
    fun doLogin(@Header("login") login:String,
                @Header("pass") pass : String): Call<LoginResult>

    @POST("uldbs-back/user/register")
    fun register(@Body user: User):Call<RegisterResult>
}