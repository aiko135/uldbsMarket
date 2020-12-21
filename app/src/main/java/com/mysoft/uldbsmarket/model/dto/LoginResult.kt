package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.User

class LoginResult(
    @SerializedName("result")
    val result : Boolean,
    @SerializedName("token")
    val token : String,
    @SerializedName("user")
    val user : User
)