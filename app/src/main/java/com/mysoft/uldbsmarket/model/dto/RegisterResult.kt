package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.User

data class RegisterResult(
    @SerializedName("result")
    val result : Boolean,
    @SerializedName("message")
    val message : String,
    @SerializedName("createdAccount")
    val createdAccount : User?
)