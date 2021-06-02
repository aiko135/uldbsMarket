package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.util.DateTypeAdapter
import java.util.*

data class Message(
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("user")
    val user: String,
    @SerializedName("chat")
    val chat: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("timestamp")
    @JsonAdapter(DateTypeAdapter::class)
    val timestamp: Date

)