package com.mysoft.uldbsmarket.network.repositories

import android.util.Log
import com.google.gson.Gson
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.network.UserAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class UserRepository(private val userAPI: UserAPI) {
    val tag: String = "UserRepository-network";
    val userDataFile = "user.pb"

    fun  doLogin(
        login:String,
        pass:String,
        onSuccess:(LoginResult)->Unit,
        onError:()->Unit ){

        CoroutineScope(Dispatchers.IO).launch {
            val call : Call<LoginResult>;
            var res : Response<LoginResult>? = null;
            try {
                call = userAPI.doLogin(login,pass);
                res = call.execute();
            }catch (e:Exception){
                Log.e(tag, "exception: " + e.message);
                Log.e(tag, "exception: " + e.toString());
                onError.invoke();
            }
            finally {
                if(res != null && res.isSuccessful) {
                    val result: LoginResult? = res.body()
                    if (result != null)
                        onSuccess(result as LoginResult)
                    else
                        onError.invoke();
                }else{
                    onError.invoke();
                }
            }
        }
    }


}