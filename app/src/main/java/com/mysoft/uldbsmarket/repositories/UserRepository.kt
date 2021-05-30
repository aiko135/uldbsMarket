package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResultDto
import com.mysoft.uldbsmarket.model.dto.RegisterResultDto
import com.mysoft.uldbsmarket.network.UserAPI
import retrofit2.Call
import retrofit2.Response

class UserRepository(private val userAPI: UserAPI, private val context : Context) {
    private val tag: String = "UserRepository-network";
    private val USER_PREF_KEY : String = "userds";
    private val SHARED_PREFERENCES_USER_FILE = "userdata"

   suspend fun doLogin(login:String, pass:String) : LoginResultDto{
            val call : Call<LoginResultDto>;
            var res : Response<LoginResultDto>? = null;
            try {
                call = userAPI.doLogin(login,pass);
                res = call.execute();
            }catch (e:Exception){
                Log.e(tag, "exception: " + e.message);
                Log.e(tag, "exception: " + e.toString());
                e.printStackTrace();
                //return LoginResult(false,"Network error","",null);
            }
            finally {
                if(res != null && res.isSuccessful) {
                    var result: LoginResultDto =
                        res.body() ?:
                        LoginResultDto(false, context.getString(R.string.response_empty_error),"",null)
                    return result
                }else{
                    return LoginResultDto(false,context.getString(R.string.request_err),"",null)
                }
            }
    }

    suspend fun register(u : User) : RegisterResultDto{
        val call : Call<RegisterResultDto>
        var res : Response<RegisterResultDto>? = null;
        try {
            call = userAPI.register(u);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            e.printStackTrace()
        }
        finally {
            if(res != null && res.isSuccessful){
                var result : RegisterResultDto =
                    res.body() ?:
                    RegisterResultDto(false, context.getString(R.string.response_empty_error),null);
                return result
            }else{
                return  RegisterResultDto(false, context.getString(R.string.request_err),null)
            }
        }
    }


    suspend fun writeUserPref(user : User ){
        val json : String  = Gson().toJson(user);
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_USER_FILE, Context.MODE_PRIVATE);
        with(sharedPref.edit()){
            putString(USER_PREF_KEY,json)
            commit()
        }
    }

    suspend fun readUserPref():User?{
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_USER_FILE, Context.MODE_PRIVATE);
        val found = sharedPref.getString(USER_PREF_KEY,null)
        if(sharedPref.contains(USER_PREF_KEY) && found != null){
            return Gson().fromJson(found,User::class.java)
        }
        else{
            return null
        }
    }

    suspend fun deleteUserPref(){
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_USER_FILE, Context.MODE_PRIVATE);
        if(sharedPref.contains(USER_PREF_KEY))
            with(sharedPref.edit()){
                remove(USER_PREF_KEY)
                clear()
                commit()
            }
    }


}