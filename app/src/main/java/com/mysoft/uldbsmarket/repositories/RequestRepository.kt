package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Status
import com.mysoft.uldbsmarket.model.dto.*
import com.mysoft.uldbsmarket.network.RequestAPI
import retrofit2.Call
import retrofit2.Response
import java.util.*

class RequestRepository(private val requestAPI: RequestAPI, private val context : Context) {
    private val tag: String = "RequestRepository-network";

    suspend fun postNewOrder(newOrder:UsersRequestDto) : RequestResult<Boolean> {
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
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }

    suspend fun getMyOrders(user_id: UUID, isManager:Boolean = false) : RequestResult< List<MyRequestDto> > {
        val call : Call<List<MyRequestDto>>
        var res : Response<List<MyRequestDto>>? = null;
        try {
            call = if(isManager)
                requestAPI.getManagerRequests(user_id.toString())
            else
                requestAPI.getClientRequests(user_id.toString());
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }

    suspend fun getAllStatusList() : RequestResult< List<Status> > {
        val call : Call<List<Status>>
        var res : Response<List<Status>>? = null;
        try {
            call = requestAPI.getAllStatus();
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            return if(res != null && res.isSuccessful) {
                if(res.body() != null)
                    RequestSuccess(res.body()!!)
                else
                    RequestError(context.getString(R.string.response_empty_error))
            }else{
                RequestError (context.getString(R.string.request_err))
            }
        }
    }
}