package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.dto.*
import retrofit2.Call
import retrofit2.Response

abstract class AbstractRepository(_context: Context) {

    protected val context: Context = _context

    abstract val tag: String

    protected fun <T> executeRequest(call : Call<T>): RequestResult<T> {
        var res : Response<T>? = null;
        try {
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