package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.repositories.UserRepository
import com.mysoft.uldbsmarket.util.Util

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _loginResultLiveData = MutableLiveData<LoginResult>();
    val loginResultLiveData: LiveData<LoginResult>
        get() = _loginResultLiveData;

    fun doLogin(login:String, pass:String, onError:()->Unit){
        val hash_pass : String = Util.md5(pass);
        userRepository.doLogin(login,hash_pass,
            {res ->
                run {
                    _loginResultLiveData.postValue(res);
                }
            },
            { onError.invoke()}
        )
    }

    //TODO не передавать сюда коллбэк. Все должно в MutableData храниться и обсервиться
    fun readUserInfo(onFinish:(User?)->Unit ){
        userRepository.readUserPref(onFinish);
    }

    fun writeUserInfo(user : User, onFinish:()->Unit){
        userRepository.writeUserPref(user, onFinish);
    }
}