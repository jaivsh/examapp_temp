package com.app.examprep.payment.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examprep.data.OrderPostDetailsData
import com.app.examprep.data.OrderResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewmodel : ViewModel() {


    private var _orderCreatedLive = MutableLiveData<OrderResponseData>()
    var orderCreatedLive : LiveData<OrderResponseData> = _orderCreatedLive
    private var _errorOrderCreatedLive = MutableLiveData<String>()
    var errorOrderCreatedLive : LiveData<String> = _errorOrderCreatedLive



    fun getOrderID(orderPostDetailsData: OrderPostDetailsData) = viewModelScope.launch(Dispatchers.IO) {
        try{
            val response = RetrofitInstance.api.getOrderId(orderPostDetailsData)
            if (response.isSuccessful){
                response.body()?.let {
                    _orderCreatedLive.postValue(it)
                }
            }else{
                _errorOrderCreatedLive.postValue("Response not Successful")
            }
        }catch (e: Exception){
            _errorOrderCreatedLive.postValue(e.message)
        }

    }



}