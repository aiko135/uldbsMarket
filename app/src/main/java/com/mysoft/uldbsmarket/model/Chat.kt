package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("clientUuid")
    val clientUuid: User,
    @SerializedName("managerUuid")
    val managerUuid: User
)