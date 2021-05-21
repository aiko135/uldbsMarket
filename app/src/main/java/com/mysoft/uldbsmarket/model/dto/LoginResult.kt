package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.User

data class LoginResult(
    @SerializedName("result")
    val result : Boolean,
    @SerializedName("error")
    val error: String,
    @SerializedName("token")
    val token : String,
    @SerializedName("user")
    val user : User?
)