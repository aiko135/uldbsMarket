package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Parametr (
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("question")
    val question : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("good")
    val good : String
)