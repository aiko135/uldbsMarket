package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.GoodRequest
import com.mysoft.uldbsmarket.model.StatusHistory
import com.mysoft.uldbsmarket.util.Util
import java.util.*

data class MyRequestDto (
    @SerializedName("goodRequestList")
    val goodRequestList : List<GoodRequest>,
    @SerializedName("contactorEmail")
    val contactorEmail : String,
    @SerializedName("contactorName")
    val contactorName : String,
    @SerializedName("contactorPhone")
    val contactorPhone : String,
    @SerializedName("contactorUuid")
    val contactorUuid : UUID,
    @SerializedName("requestUuid")
    val requestUuid : UUID,

    @SerializedName("statusHistoryList")
    val statusHistoryList : List<StatusHistory>,
)