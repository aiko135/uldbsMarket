package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.repositories.UserRepository

class ProfileViewModel(private val userRepository: UserRepository):ViewModel() {
    private val _userData = MutableLiveData<User>();
    val userData: LiveData<User>
        get() = _userData;

    fun loadUserData(){
        userRepository.readUserPref{
            if(it != null)
                _userData.postValue(it);
        };
    }

    fun signOut(onFinish:()->Unit){
        userRepository.deleteUserPref(onFinish);
    }
}