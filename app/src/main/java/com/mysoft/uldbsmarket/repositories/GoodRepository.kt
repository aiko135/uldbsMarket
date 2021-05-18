package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.ReqResult
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.network.GoodAPI
import retrofit2.Call
import retrofit2.Response

class GoodRepository(private val goodAPI: GoodAPI, private val context : Context) {
    private val tag: String = "GoodRepository-network";

    suspend fun getAllGoods(): ReqResult<List<Good>> {
        val call : Call<List<Good>>
        var res : Response<List<Good>>? = null;
        try {
            call = goodAPI.getAllGoods();
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace();
        }
        finally {
            if(res != null && res.isSuccessful) {
                return if(res.body() == null)
                    ReqResult(false, context.getString(R.string.response_empty_error),null)
                else
                    ReqResult(true, "", res.body())

            }else{
                return ReqResult(false, context.getString(R.string.request_err),null)
            }
        }
    }

    suspend fun getGoodInfo(goodId : String):FullGoodInfo?{
        val call : Call<FullGoodInfo>
        var res : Response<FullGoodInfo>? = null;
        try {
            call = goodAPI.getFullInfoForGood(goodId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace();
        }
        finally {
            if(res != null && res.isSuccessful){
                val result : FullGoodInfo? = res.body();
                return result;
            }
            return null
        }
    }
}