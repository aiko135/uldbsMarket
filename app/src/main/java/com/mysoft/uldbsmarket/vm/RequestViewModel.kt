package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.ReqResult
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.model.dto.UsersRequest
import com.mysoft.uldbsmarket.repositories.RequestRepository
import com.mysoft.uldbsmarket.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RequestViewModel(private val requestRepository: RequestRepository): ViewModel() {

    //Удачно или неудачно заказ добавлен в систему
    private val _isNewOrderAddedSLD = SingleLiveEvent<ReqResult<Boolean>>();
    val isNewOrderAddedSLD : LiveData<ReqResult<Boolean>>
        get() = _isNewOrderAddedSLD;

    fun postNewOrder(
        current_user: UUID,
        paymentData:String,
        goods:List<Good>
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val res = requestRepository.postNewOrder(UsersRequest(current_user,paymentData,goods))
            _isNewOrderAddedSLD.postValue(res)
        }
    }
}