package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName

data class Parametr (
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("question")
    val question : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("goodUuid")
    val goodUuid : String
)