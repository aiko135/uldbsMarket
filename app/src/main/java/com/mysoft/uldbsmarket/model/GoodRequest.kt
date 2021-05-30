package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class GoodRequest (
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("good")
    val good : Good,
    @SerializedName("request")
    val request : UUID,
    )