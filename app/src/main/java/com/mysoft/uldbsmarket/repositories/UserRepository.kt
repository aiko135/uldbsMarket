package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResultDto
import com.mysoft.uldbsmarket.model.dto.RegisterResultDto
import com.mysoft.uldbsmarket.model.dto.RequestResult
import com.mysoft.uldbsmarket.network.UserAPI
import retrofit2.Call
import retrofit2.Response

// TODO --- SRP principle
class UserRepository(
    private val userAPI: UserAPI,
    context : Context
) : AbstractRepository(context) {
    override val tag: String = "UserRepository-network";

    private val USER_PREF_KEY : String = "userds";
    private val SHARED_PREFERENCES_USER_FILE = "userdata"

   suspend fun doLogin(login:String, pass:String) : RequestResult<LoginResultDto> {
       val call : Call<LoginResultDto> = userAPI.doLogin(login,pass);
       return super.executeRequest(call);
   }

    suspend fun register(u : User) : RequestResult<RegisterResultDto>{
        val call : Call<RegisterResultDto> = userAPI.register(u);
        return super.executeRequest(call);
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