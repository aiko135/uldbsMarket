package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Status (
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("name")
    val name : String,
    @SerializedName("isInitial")
    val isInitial : Int,
    @SerializedName("isTerminal")
    val isTerminal : Int,
)