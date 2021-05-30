package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class StatusHistory(
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("comment")
    val comment : String,
    @SerializedName("setupTimestamp")
    val setupTimestamp: Date,
    @SerializedName("status")
    val status : Status,
)