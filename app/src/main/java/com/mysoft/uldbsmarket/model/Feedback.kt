package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Feedback (
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("user")
    val user : User?,
    @SerializedName("timestamp")
    val timestamp : String,
    @SerializedName("grade")
    val grade : Int,
    @SerializedName("feedback")
    val feedback : String?,
    @SerializedName("good")
    val good : String
)