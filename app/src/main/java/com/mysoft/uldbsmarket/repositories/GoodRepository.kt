package com.mysoft.uldbsmarket.repositories

import android.util.Log
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.network.GoodAPI
import retrofit2.Call
import retrofit2.Response

class GoodRepository(private val goodAPI: GoodAPI) {
    private val tag: String = "GoodRepository-network";

    suspend fun getAllGoods():List<Good>?{
        val call : Call<List<Good>>
        var res : Response<List<Good>>? = null;
        try {
            call = goodAPI.getAllGoods();
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            return null
        }
        finally {
            if(res != null && res.isSuccessful){
                val result : List<Good>? = res.body();
                if(result != null)
                    return result;
            }
            return null
        }
    }
}