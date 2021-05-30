package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Chat(
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("client")
    val client: User,
    @SerializedName("manager")
    val manager: User
)