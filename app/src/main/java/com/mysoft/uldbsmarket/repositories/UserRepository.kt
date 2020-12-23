package com.mysoft.uldbsmarket.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.network.UserAPI
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class UserRepository(private val userAPI: UserAPI, private val context : Context) {
    private val tag: String = "UserRepository-network";
    private val USER_PREF : String = "userds";
    private val USER_RECORD_KEY = preferencesKey<String>("user_record")


    private val dataStore : DataStore<Preferences> by lazy{
        context.createDataStore(
            name = USER_PREF,
            corruptionHandler = null,
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO + Job())
        )
    }

    fun  doLogin(login:String, pass:String) : LoginResult?{
            val call : Call<LoginResult>;
            var res : Response<LoginResult>? = null;
            try {
                call = userAPI.doLogin(login,pass);
                res = call.execute();
            }catch (e:Exception){
                Log.e(tag, "exception: " + e.message);
                Log.e(tag, "exception: " + e.toString());
                return null;
            }
            finally {
                if(res != null && res.isSuccessful) {
                    val result: LoginResult? = res.body()
                    return result
                }else{
                    return  null
                }
            }
    }

    suspend fun register(u : User):RegisterResult?{
        val call : Call<RegisterResult>
        var res : Response<RegisterResult>? = null;
        try {
            call = userAPI.register(u);
            res = call.execute();
        }catch (e:Exception){
            Log.e(tag, "exception: " + e.message);
            Log.e(tag, "exception: " + e.toString());
            return null
        }
        finally {
            if(res != null && res.isSuccessful){
                val result : RegisterResult? = res.body();
                if(result != null)
                    return result;
            }
            return null
        }
    }

    //TODO асинхронность на уровне VM
    fun writeUserPref(user : User, onFinish:()->Unit ){
        CoroutineScope(Dispatchers.IO).launch {
            val json : String  = Gson().toJson(user);
            dataStore.updateData { prefs ->
                prefs.toMutablePreferences().apply {
                    set(USER_RECORD_KEY, json)
                }
            }
            onFinish();
        }
    }
    //TODO асинхронность на уровне VM
    fun readUserPref(onFinish:(User?)->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { settings ->
                val data : String = settings[USER_RECORD_KEY] ?: "";
                if(data == "")
                    onFinish.invoke(null)
                else
                    Gson().fromJson(data,User::class.java).let{
                        onFinish.invoke(it);
                    }
            }
        }
    }

    suspend fun writeUserPref_sync(user : User, onFinish:()->Unit ){
            val json : String  = Gson().toJson(user);
            dataStore.updateData { prefs ->
                prefs.toMutablePreferences().apply {
                    set(USER_RECORD_KEY, json)
                    onFinish.invoke();
                }
            }
    }

    suspend fun readUserPref_sync(onFinish:(User?)->Unit){
        dataStore.edit { record ->
            val data : String = record[USER_RECORD_KEY] ?: "";
            if(data == "")
                onFinish.invoke(null)
            else
                Gson().fromJson(data,User::class.java).let{
                    onFinish.invoke(it);
                }
        }
    }
    //-----------------------------------------------------------------------

    //TODO асинхронность на уровне VM
    fun deleteUserPref(onFinish:()->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.updateData { store->
                store.toMutablePreferences().apply {
                    set(USER_RECORD_KEY, "")
                }
            }
            onFinish.invoke();
        }
    }


}