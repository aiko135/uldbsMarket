package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName

data class Feedback (
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("userUuid")
    val userUuid : User?,
    @SerializedName("timestamp")
    val timestamp : String,
    @SerializedName("grade")
    val grade : Int,
    @SerializedName("feedback")
    val feedback : String?,
    @SerializedName("goodUuid")
    val goodUuid : String
)