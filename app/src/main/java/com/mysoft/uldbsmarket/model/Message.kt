package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Message(
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("userUuid")
    val userUuid: String,
    @SerializedName("chatUuid")
    val chatUuid: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("timestamp")
    val timestamp: Date

)