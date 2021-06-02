package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.util.DateTypeAdapter
import java.util.*

data class Feedback (
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("user")
    val user : User?,
    @SerializedName("timestamp")
    @JsonAdapter(DateTypeAdapter::class)
    val timestamp : Date,
    @SerializedName("grade")
    val grade : Int,
    @SerializedName("feedback")
    var feedback : String?,
    @SerializedName("good")
    val good : String
)