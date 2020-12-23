package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GoodAPI {
    @GET("/uldbs-back/good/getGoods")
    fun getAllGoods(): Call<List<Good>>

    @GET("/uldbs-back/good/getFullInfoForGood/{goodid}")
    fun getFullInfoForGood(@Path("goodid") goodUuid : String): Call<FullGoodInfo>
}