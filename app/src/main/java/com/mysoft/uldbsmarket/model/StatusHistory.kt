package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.util.DateTypeAdapter
import java.util.*

data class StatusHistory(
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("comment")
    val comment : String,
    @SerializedName("setupTimestamp")
    @JsonAdapter(DateTypeAdapter::class)
    val setupTimestamp: Date,
    @SerializedName("status")
    val status : Status,
)