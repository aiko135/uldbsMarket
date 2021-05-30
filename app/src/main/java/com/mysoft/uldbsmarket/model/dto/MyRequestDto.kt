package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.GoodRequest
import com.mysoft.uldbsmarket.model.StatusHistory
import java.util.*

data class MyRequestDto (
    @SerializedName("goodRequestList")
    val goodRequestList : List<GoodRequest>,
    @SerializedName("managerEmail")
    val managerEmail : String,
    @SerializedName("managerName")
    val managerName : String,
    @SerializedName("managerPhone")
    val managerPhone : String,
    @SerializedName("managerUuid")
    val managerUuid : UUID,
    @SerializedName("requestUuid")
    val requestUuid : UUID,

    @SerializedName("statusHistoryList")
    val statusHistoryList : List<StatusHistory>,

)