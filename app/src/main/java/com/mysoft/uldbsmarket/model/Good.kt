package com.mysoft.uldbsmarket.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Good (
    @SerializedName("uuid")
    val uuid : UUID,
    @SerializedName("catalog")
    val catalog : String,
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
){
    companion object {
        fun SummPrice(goods:List<Good>):Float{
            var result : Float = 0.0f;
            goods.forEach{
                result += it.price;
            }
            return result
        }
    }
}