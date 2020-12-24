package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.readUserPref_sync().let {
                if(it != null)
                    _userData.postValue(it);
            }
        }
    }
    //TOD сделать асинхронно
    fun signOut(){
        //CoroutineScope(Dispatchers.IO).launch {
            userRepository.deleteUserPref_sync();
        //}
    }
}