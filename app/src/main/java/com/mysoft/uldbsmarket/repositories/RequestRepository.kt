package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.dto.MyRequestDto
import com.mysoft.uldbsmarket.model.dto.ReqResult
import com.mysoft.uldbsmarket.model.dto.UsersRequestDto
import com.mysoft.uldbsmarket.network.RequestAPI
import retrofit2.Call
import retrofit2.Response
import java.util.*

class RequestRepository(private val requestAPI: RequestAPI, private val context : Context) {
    private val tag: String = "RequestRepository-network";

    suspend fun postNewOrder(newOrder:UsersRequestDto) : ReqResult<Boolean> {
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
                    ReqResult(false, context.getString(R.string.response_empty_error), null)
                else
                    ReqResult(true, "", res.body())
            }else{
                return ReqResult(false, context.getString(R.string.request_err), null)
            }
        }
    }

    suspend fun getMyOrders(user_id: UUID) : ReqResult< List<MyRequestDto> > {
        val call : Call<List<MyRequestDto>>
        var res : Response<List<MyRequestDto>>? = null;
        try {
            call = requestAPI.getMyRequests(user_id.toString());
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            if(res != null && res.isSuccessful) {
                return if(res.body() == null)
                    ReqResult(
                        false,
                        context.getString(R.string.response_empty_error),
                        null
                    )
                else
                    ReqResult(
                        true,
                        "",
                        res.body()
                    )
            }else{
                return ReqResult(
                    false,
                    context.getString(R.string.request_err),
                    null
                )
            }
        }
    }
}