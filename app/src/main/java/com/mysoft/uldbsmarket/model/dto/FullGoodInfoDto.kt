package com.mysoft.uldbsmarket.model.dto

import com.google.gson.annotations.SerializedName
import com.mysoft.uldbsmarket.model.Feedback
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.User

data class FullGoodInfoDto(
    @SerializedName("good")
    val good : Good,
    @SerializedName("feedbacks")
    val feedbacks : List<Feedback>
)