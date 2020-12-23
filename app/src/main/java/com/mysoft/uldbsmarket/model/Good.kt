package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName

data class Good (
    @SerializedName("uuid")
    val uuid : String,
    @SerializedName("catalogUuid")
    val catalogUuid : String,
    @SerializedName("descr")
    val descr: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("imgPath")
    val imgPath: String?,
    @SerializedName("parametrList")
    val parametrList: List<Parametr>
)