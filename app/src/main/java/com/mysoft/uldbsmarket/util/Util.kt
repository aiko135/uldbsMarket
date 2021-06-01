package com.mysoft.uldbsmarket.util


import com.mysoft.uldbsmarket.model.Status
import com.mysoft.uldbsmarket.model.StatusHistory
import java.math.BigInteger
import java.security.MessageDigest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun md5(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

        fun dateToFormattedString(date: Date):String{
            var df : DateFormat = SimpleDateFormat("d MMM yyyy" , Locale.getDefault())
            return df.format(date);
        }

        fun priceToString(price:Float):String{
            return "%.2f".format(price)
        }

        fun getCurrentStatusFromHistory(history : List<StatusHistory>): StatusHistory{
            var first : StatusHistory = history[0];
            for(i in 1 until history.size){
                val next = history[i];
                if(next.setupTimestamp.after(first.setupTimestamp))
                    first = next;
            }
            return first;
        }
    }
}