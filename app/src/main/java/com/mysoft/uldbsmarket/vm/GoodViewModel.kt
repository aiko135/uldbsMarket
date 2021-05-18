package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.ReqResult
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.repositories.GoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoodViewModel(private val goodRepository: GoodRepository):ViewModel() {
    private val _goodsLD = MutableLiveData< ReqResult<List<Good>> >();
    val goodsLD: LiveData< ReqResult<List<Good>> >
        get () = _goodsLD;

    private val _selectedGoodLD = MutableLiveData<FullGoodInfo>();
    val selectedGoodLD : LiveData<FullGoodInfo>
        get() = _selectedGoodLD;

    var goodid : String? = null

    fun getFullGoodData(onError : ()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            var res : FullGoodInfo? = null

            if(goodid != null){
                res =  goodRepository.getGoodInfo(goodid!!);
            }

            if(res == null)
                onError.invoke()
            else
                _selectedGoodLD.postValue(res)
        }
    }

    fun loadGoods(){
        viewModelScope.launch(Dispatchers.IO){
            val result = goodRepository.getAllGoods();
            _goodsLD.postValue(result);
        }
    }
}