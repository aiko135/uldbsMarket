package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResultDto
import com.mysoft.uldbsmarket.model.dto.RegisterResultDto
import com.mysoft.uldbsmarket.model.dto.RequestResult
import com.mysoft.uldbsmarket.model.dto.RequestSuccess
import com.mysoft.uldbsmarket.repositories.UserRepository
import com.mysoft.uldbsmarket.util.SingleLiveEvent
import com.mysoft.uldbsmarket.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _loginResultSLD = SingleLiveEvent<RequestResult<LoginResultDto>>();
    val loginResultLD: LiveData<RequestResult<LoginResultDto>>
        get() = _loginResultSLD; //Инкапсуляция возможности обновлять LD данные

    private val _registerResultSLD = SingleLiveEvent<RequestResult<RegisterResultDto>>();
    val registerResultLD: LiveData<RequestResult<RegisterResultDto>>
        get() = _registerResultSLD;

    private val _userLD = MutableLiveData<User?>();
    val userLD: LiveData<User?>
        get() = _userLD;

    fun doLogin(login:String, pass:String){
        viewModelScope.launch(Dispatchers.IO) {
            val hash_pass : String = Util.md5(pass);
            val response = userRepository.doLogin(login,hash_pass);
            _loginResultSLD.postValue(response);
            if(response is RequestSuccess && response.entity.result && response.entity.user != null){
                //Успешная авторизация
                userRepository.writeUserPref(response.entity.user)
                _userLD.postValue(response.entity.user);
            }
        }
    }

    fun register(u: User){
        viewModelScope.launch(Dispatchers.IO) {
            val response  = userRepository.register(u);
            _registerResultSLD.postValue(response);
            if(response is RequestSuccess && response.entity.result && response.entity.createdAccount != null){
                //Успешная регистрация
                userRepository.writeUserPref(response.entity.createdAccount)
                _userLD.postValue(response.entity.createdAccount);
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