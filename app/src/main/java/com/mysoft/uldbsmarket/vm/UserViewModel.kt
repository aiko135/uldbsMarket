package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.repositories.UserRepository
import com.mysoft.uldbsmarket.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _loginResultLD = MutableLiveData<LoginResult>();
    val loginResultLD: LiveData<LoginResult>
        get() = _loginResultLD; //Инкапсуляция возможности обновлять LD данные

    private val _registerResultLD = MutableLiveData<RegisterResult>();
    val registerResultLD: LiveData<RegisterResult>
        get() = _registerResultLD;

    private val _userLD = MutableLiveData<User?>();
    val userLD: LiveData<User?>
        get() = _userLD;

    fun doLogin(login:String, pass:String){
        viewModelScope.launch(Dispatchers.IO) {
            val hash_pass : String = Util.md5(pass);
            val data = userRepository.doLogin(login,hash_pass);
            _loginResultLD.postValue(data);
            if(data.result && data.user != null){
                //Успешная авторизация
                userRepository.writeUserPref(data.user)
                _userLD.postValue(data.user);
            }
        }
    }

    fun register(u: User){
        viewModelScope.launch(Dispatchers.IO) {
            val result : RegisterResult = userRepository.register(u);
            _registerResultLD.postValue(result);
            if(result.result && result.createdAccount != null){
                //Успешная регистрация
                userRepository.writeUserPref(result.createdAccount)
                _userLD.postValue(result.createdAccount);
            }
        }
    }

    fun readUserInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.readUserPref().let {
                _userLD.postValue(it)
            };
        }
    }

    fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUserPref();
        }
        _userLD.postValue(null);
    }
}