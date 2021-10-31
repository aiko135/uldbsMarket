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

class RequestRepository(
    private val requestAPI: RequestAPI,
    context : Context
) : AbstractRepository(context) {
    override val tag: String = "RequestRepository-network";

    suspend fun postNewOrder(newOrder:UsersRequestDto) : RequestResult<Boolean> {
        val call : Call<Boolean> = requestAPI.postRequest(newOrder);
        return super.executeRequest(call);
    }

    suspend fun getMyOrders(user_id: UUID, isManager:Boolean = false) : RequestResult< List<MyRequestDto> > {
        val call : Call<List<MyRequestDto>> = if(isManager)
            requestAPI.getManagerRequests(user_id.toString())
        else
            requestAPI.getClientRequests(user_id.toString());
        return super.executeRequest(call);
    }

    suspend fun getAllStatusList() : RequestResult< List<Status> > {
        val call : Call<List<Status>> = requestAPI.getAllStatus();
        return super.executeRequest(call);
    }
}