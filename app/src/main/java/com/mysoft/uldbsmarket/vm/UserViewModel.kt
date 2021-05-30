package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResultDto
import com.mysoft.uldbsmarket.model.dto.RegisterResultDto
import com.mysoft.uldbsmarket.repositories.UserRepository
import com.mysoft.uldbsmarket.util.SingleLiveEvent
import com.mysoft.uldbsmarket.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _loginResultSLD = SingleLiveEvent<LoginResultDto>();
    val loginResultLD: LiveData<LoginResultDto>
        get() = _loginResultSLD; //Инкапсуляция возможности обновлять LD данные

    private val _registerResultSLD = SingleLiveEvent<RegisterResultDto>();
    val registerResultLD: LiveData<RegisterResultDto>
        get() = _registerResultSLD;

    private val _userLD = MutableLiveData<User?>();
    val userLD: LiveData<User?>
        get() = _userLD;

    fun doLogin(login:String, pass:String){
        viewModelScope.launch(Dispatchers.IO) {
            val hash_pass : String = Util.md5(pass);
            val data = userRepository.doLogin(login,hash_pass);
            _loginResultSLD.postValue(data);
            if(data.result && data.user != null){
                //Успешная авторизация
                userRepository.writeUserPref(data.user)
                _userLD.postValue(data.user);
            }
        }
    }

    fun register(u: User){
        viewModelScope.launch(Dispatchers.IO) {
            val result : RegisterResultDto = userRepository.register(u);
            _registerResultSLD.postValue(result);
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