package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.util.DateTypeAdapter
import java.util.*

data class User (
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("pass")
    val pass : String,
    @SerializedName("role")
    val role: Short,
    @SerializedName("name")
    val name: String,
    @SerializedName("birthDate")
    @JsonAdapter(DateTypeAdapter::class)
    val birthDate: Date,
    @SerializedName("phone")
    val phone: String

)