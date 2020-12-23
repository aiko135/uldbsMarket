package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.repositories.GoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogViewModel(private val goodRepository: GoodRepository):ViewModel() {
    private val _goods = MutableLiveData<List<Good>>();
    val goods: LiveData<List<Good>>
        get() = _goods;

    fun loadChats(onError:()->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            var result : List<Good>?= goodRepository.getAllGoods();
            if(result == null)
                onError.invoke();
            else
                _goods.postValue(result);
        }
    }
}