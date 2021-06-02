package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.Status
import com.mysoft.uldbsmarket.model.dto.MyRequestDto
import com.mysoft.uldbsmarket.model.dto.ReqResult
import com.mysoft.uldbsmarket.model.dto.UsersRequestDto
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

    private val _myOrdersLD = MutableLiveData<ReqResult<List<MyRequestDto>>>();
    val myOrdersLD: LiveData<ReqResult<List<MyRequestDto>>>
        get () = _myOrdersLD;

    private val _allStatusLD = MutableLiveData<ReqResult<List<Status>>>();
    val allStatusLD: LiveData<ReqResult<List<Status>>>
        get() = _allStatusLD;

    fun postNewOrder(
        current_user: UUID,
        paymentData:String,
        goods:List<Good>
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val res = requestRepository.postNewOrder(UsersRequestDto(current_user,paymentData,goods))
            _isNewOrderAddedSLD.postValue(res)
        }
    }

    fun getOrders(current_user: UUID, isManagerMode:Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val res = requestRepository.getMyOrders(current_user, isManagerMode);
            _myOrdersLD.postValue(res);
        }
    }

    fun loadAllStatusList(){
        viewModelScope.launch(Dispatchers.IO) {
            val res = requestRepository.getAllStatusList()
            _allStatusLD.postValue(res)
        }
    }
}