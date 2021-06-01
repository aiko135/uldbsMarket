package com.mysoft.uldbsmarket.network

import com.mysoft.uldbsmarket.model.Feedback
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.dto.FullGoodInfoDto
import retrofit2.Call
import retrofit2.http.*

interface GoodAPI {
    @GET("/uldbs-back/good/getGoods")
    fun getAllGoods(): Call<List<Good>>

    @GET("/uldbs-back/good/getFullInfoForGood/{goodid}")
    fun getFullInfoForGood(@Path("goodid") goodUuid : String): Call<FullGoodInfoDto>

    @POST("/uldbs-back/feedback/postFeedback")
    fun postFeedback(
        @Header("user") userId:String,
        @Header("good") goodId:String,
        @Body feedback: Feedback
    ): Call<Boolean>
}