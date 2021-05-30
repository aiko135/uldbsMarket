package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.ReqResult
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.model.dto.UsersRequest
import com.mysoft.uldbsmarket.network.GoodAPI
import com.mysoft.uldbsmarket.network.RequestAPI
import retrofit2.Call
import retrofit2.Response
import java.util.*

class RequestRepository(private val requestAPI: RequestAPI, private val context : Context) {
    private val tag: String = "RequestRepository-network";

    suspend fun postNewOrder(newOrder:UsersRequest) : ReqResult<Boolean> {
        val call : Call<Boolean>
        var res : Response<Boolean>? = null;
        try {
            call = requestAPI.postRequest(newOrder);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
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
}