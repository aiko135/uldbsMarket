package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.Good
import java.util.*

data class UsersRequest(
    @SerializedName("userUuid")
    val userUuid : UUID,
    @SerializedName("paymentData")
    val paymentData : String,
    @SerializedName("goods")
    val goods : List<Good>
)