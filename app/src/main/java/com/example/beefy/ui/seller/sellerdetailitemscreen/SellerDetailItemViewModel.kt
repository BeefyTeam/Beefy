package com.example.beefy.ui.seller.sellerdetailitemscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DeleteProductResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class SellerDetailItemViewModel(val sellerRepository: SellerRepository):ViewModel() {
    private var _delete = MutableLiveData<Resource<DeleteProductResponse>>()
    val delete : LiveData<Resource<DeleteProductResponse>> = _delete

    fun deleteProduct(id:Int){
        viewModelScope.launch {
            sellerRepository.deleteProduct(id).collect{
                _delete.postValue(it)
            }
        }
    }

}