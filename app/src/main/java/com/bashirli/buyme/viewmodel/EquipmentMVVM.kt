package com.bashirli.buyme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.repo.apirepo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class EquipmentMVVM @Inject constructor(
     var repo: Repo
) :ViewModel() {

    private var _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
    get()=_loading

    private var _errorData=MutableLiveData<Boolean>()
    val errorData:LiveData<Boolean>
        get()=_errorData

    private var _success=MutableLiveData<Boolean>()
    val success:LiveData<Boolean>
        get()=_success

    private var _responseData=MutableLiveData<List<ProductData>>()
    val responseData:LiveData<List<ProductData>>
        get()=_responseData

    var errorMessage:String?=null

    private var exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        _loading.value=false
        errorMessage=throwable.localizedMessage
        _errorData.value=true
    }


    init {
        loadData()
    }

    private var job: Job?=null

    private fun loadData(){
        _loading.value=true
        _errorData.value=false
        errorMessage=null
        _success.value=false

        try{
            job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                var response=repo.getProducts()
                withContext(Dispatchers.Main){
                    response.data?.let {
                        _responseData.value=it
                        _loading.value=false
                        _success.value=true
                    }
                }
            }
        }catch (e:Exception){
            _loading.value=false
            errorMessage=e.localizedMessage
            _errorData.value=true

        }

    }

    fun updateData(){
        loadData()
    }


}