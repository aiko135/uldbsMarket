package com.mysoft.uldbsmarket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysoft.uldbsmarket.model.Feedback
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.dto.ReqResult
import com.mysoft.uldbsmarket.model.dto.FullGoodInfoDto
import com.mysoft.uldbsmarket.repositories.GoodRepository
import com.mysoft.uldbsmarket.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GoodViewModel(private val goodRepository: GoodRepository):ViewModel() {
    //Список товаров
    private val _goodsLD = MutableLiveData<ReqResult<List<Good>>>();
    val goodsLD: LiveData<ReqResult<List<Good>>>
        get () = _goodsLD;

    //Выбранынй товар
    private val _selectedGoodLD = MutableLiveData<ReqResult<FullGoodInfoDto>>();
    val selectedGoodLDDto : LiveData<ReqResult<FullGoodInfoDto>>
        get() = _selectedGoodLD;

    //Удачно или неудачно (уже в корзине) добавление в корзину
    private val _isAddedToCardSLD = SingleLiveEvent<Boolean>();
    val isAddedToCardSLD : LiveData<Boolean>
        get() = _isAddedToCardSLD;

    //Список товаров в корзине
    private val _cartLD = MutableLiveData< MutableList<Good> >();
    val cartLD : LiveData< MutableList<Good> >
        get() = _cartLD;

    private val _isFeedbackAddedSLD = SingleLiveEvent< ReqResult<Boolean>>()
    val isFeedbackAddedSLD: LiveData< ReqResult<Boolean> >
        get() = _isFeedbackAddedSLD


    var m_GoodId : String? = null

    fun getFullGoodData(){
        viewModelScope.launch(Dispatchers.IO) {
            if(m_GoodId != null) {
                val res: ReqResult<FullGoodInfoDto> = goodRepository.getGoodInfo(m_GoodId!!);
                _selectedGoodLD.postValue(res)
            }
        }
    }

    fun loadGoods(){
        viewModelScope.launch(Dispatchers.IO){
            val result = goodRepository.getAllGoods();
            _goodsLD.postValue(result);
        }
    }

    fun addSelectedGoodIntoCart(){
        viewModelScope.launch(Dispatchers.IO){
            if(selectedGoodLDDto.value != null
                && selectedGoodLDDto.value!!.isSuccess
                && selectedGoodLDDto.value!!.entity != null
            ){
                goodRepository.addGoodToCart(selectedGoodLDDto.value!!.entity!!.good)
                _isAddedToCardSLD.postValue(true);
            }
        }
    }

    //Загрузит из хранилища данные о товарах в корзине
    fun loadGoodsCart(){
        viewModelScope.launch(Dispatchers.IO){
            val goods = goodRepository.getGoodsInCart();
            if(goods != null){
                _cartLD.postValue(goods!!);
            }
        }
    }

    fun cleanCart(){
        viewModelScope.launch(Dispatchers.IO){
           goodRepository.clearCart()
            _cartLD.postValue(mutableListOf())
        }
    }

    fun getSummPrice():Float{
        if(cartLD.value != null){
            return  Good.SummPrice(cartLD.value!!)
        }
        else{
            return 0.0f
        }
    }

    fun postFeedback(userId: UUID, goodId:UUID, grade:Int, feedback : String?){
        viewModelScope.launch(Dispatchers.IO){
            val fb :String? = feedback?.replace("\n", " ")
            val res = goodRepository.postFeedback(
                userId.toString(),
                goodId.toString(),
                Feedback(
                    UUID.randomUUID(),
                    null,
                    Date(),
                    grade,
                    feedback,
                    goodId.toString()
                )
            );
            _isFeedbackAddedSLD.postValue(res)
        }
    }
}