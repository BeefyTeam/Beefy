package com.example.beefy.ui.buyer.buyerscanhistoryscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.ScanMeatHistoryResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class BuyerScanHistoryViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository):ViewModel() {

    private var _scanHistory = MutableLiveData<Resource<List<ScanMeatHistoryResponse>>>()
    val scanHistory : LiveData<Resource<List<ScanMeatHistoryResponse>>> = _scanHistory

    init {
        getScanHistory()
    }

    fun getScanHistory(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if (!it.isNullOrEmpty()){
                    buyerRepository.scanMeatHistory(it.toInt()).collect{
                        _scanHistory.postValue(it)
                    }
                }
            }
        }
    }

}