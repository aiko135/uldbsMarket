package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mysoft.uldbsmarket.model.Message
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.repositories.GoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoodViewModel(private val goodRepository: GoodRepository):ViewModel() {
    private val _goodinfo = MutableLiveData<FullGoodInfo>();
    val goodinfo : LiveData<FullGoodInfo>
        get() = _goodinfo;

    var goodid : String? = null

    fun getFullGoodData(onError : ()->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            var res : FullGoodInfo? = null

            if(goodid != null){
                res =  goodRepository.getGoodInfo(goodid!!);
            }

            if(res == null)
                onError.invoke()
            else
                _goodinfo.postValue(res)
        }
    }
}