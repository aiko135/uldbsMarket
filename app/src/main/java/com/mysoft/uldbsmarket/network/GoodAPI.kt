package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Good
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GoodAPI {
    @GET("/uldbs-back/good/getGoods")
    fun getAllGoods(): Call<List<Good>>
}