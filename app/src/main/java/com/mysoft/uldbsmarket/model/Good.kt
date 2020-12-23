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
    @SerializedName("img_path")
    val img_path: String?,
    @SerializedName("parametrList")
    val parametrList: List<Parametr>
)