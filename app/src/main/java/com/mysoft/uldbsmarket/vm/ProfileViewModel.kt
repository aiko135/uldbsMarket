package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository):ViewModel() {
    private val _userData = MutableLiveData<User>();
    val userData: LiveData<User>
        get() = _userData;



    fun loadUserData(){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.readUserPref().let {
                if(it != null)
                    _userData.postValue(it);
            }
        }
    }

    fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUserPref();
        }
    }
}