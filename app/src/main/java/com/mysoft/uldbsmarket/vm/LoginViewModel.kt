package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.repositories.UserRepository
import com.mysoft.uldbsmarket.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _loginResultLiveData = MutableLiveData<LoginResult>();
    val loginResultLiveData: LiveData<LoginResult>
        get() = _loginResultLiveData;

    private val _user = MutableLiveData<User?>();
    val user: LiveData<User?>
        get() = _user;

    fun doLogin(login:String, pass:String, onError:()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val hash_pass : String = Util.md5(pass);
            val data = userRepository.doLogin(login,hash_pass);
            if(data == null)
                onError.invoke()
            else{
                _loginResultLiveData.postValue(data);
                if(data.result){
                    //Успешная авторизация
                    if(data.user == null)
                        onError.invoke();
                    else
                        writeUserInfo(data.user);
                }
            }
        }
    }

    fun readUserInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.readUserPref().let {
                _user.postValue(it)
            };
        }
    }

    fun writeUserInfo(user : User){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.writeUserPref(user)
            _user.postValue(user);
        }
    }
}