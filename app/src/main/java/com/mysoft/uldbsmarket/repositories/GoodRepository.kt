package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Feedback
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.dto.RequestResult
import com.mysoft.uldbsmarket.model.dto.FullGoodInfoDto
import com.mysoft.uldbsmarket.model.dto.RequestError
import com.mysoft.uldbsmarket.model.dto.RequestSuccess
import com.mysoft.uldbsmarket.network.GoodAPI
import retrofit2.Call
import retrofit2.Response

class GoodRepository(private val goodAPI: GoodAPI, private val context : Context) {
    private val tag: String = "GoodRepository-network";

    private val CART_PREF_KEY : String = "goodscart";
    private val SHARED_PREFERENCES_CART_FILE = "cart"

    suspend fun getAllGoods(): RequestResult<List<Good>> {
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

    suspend fun getGoodInfo(goodId : String): RequestResult<FullGoodInfoDto> {
        val call : Call<FullGoodInfoDto>
        var res : Response<FullGoodInfoDto>? = null;
        try {
            call = goodAPI.getFullInfoForGood(goodId);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace();
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

    suspend fun addGoodToCart(good :Good){
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_CART_FILE, Context.MODE_PRIVATE);

        //Уже добавленные товары
        var goods_list : MutableList<Good>? = getGoodsInCart();
        if(goods_list == null){
            goods_list = mutableListOf(good)
        }
        else{
            if(goods_list.find { it.uuid == good.uuid } == null)
                goods_list.add(good)
        }

        //Сохранение
        val json : String  = Gson().toJson(goods_list)
        with(sharedPref.edit()){
            putString(CART_PREF_KEY,json)
            commit()
        }
    }

    suspend fun getGoodsInCart():MutableList<Good>?{
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_CART_FILE, Context.MODE_PRIVATE);

        val found = sharedPref.getString(CART_PREF_KEY,null)
        if(sharedPref.contains(CART_PREF_KEY) && found != null){
            val good_list : MutableList<Good> = Gson().fromJson(found, Array<Good>::class.java).toMutableList();
            return good_list;
        }
        else{
            return null
        }
    }

    suspend fun clearCart(){
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_CART_FILE, Context.MODE_PRIVATE);
        if(sharedPref.contains(CART_PREF_KEY))
            with(sharedPref.edit()){
                remove(CART_PREF_KEY)
                clear()
                commit()
            }
    }

    suspend fun postFeedback(userId:String, goodId:String, feedback: Feedback):RequestResult< Boolean > {
        val call : Call<Boolean>
        var res : Response<Boolean>? = null;
        try {
            call = goodAPI.postFeedback(userId, goodId, feedback);
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