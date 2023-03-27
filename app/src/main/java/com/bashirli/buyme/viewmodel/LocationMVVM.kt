package com.bashirli.buyme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashirli.buyme.model.LocationModel
import com.bashirli.buyme.repo.roomrepo.location.LocationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LocationMVVM @Inject constructor(
    private var repo: LocationRepo
) : ViewModel() {

    private var _errorData=MutableLiveData<Boolean>()
    val errorData:LiveData<Boolean>
    get()=_errorData

    private var _success=MutableLiveData<Boolean>()
    val success:LiveData<Boolean>
    get() = _success

    var errorMessage:String?=null

    private var job: Job?=null


    private var exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        errorMessage=throwable.localizedMessage
        _errorData.value=true

    }

    private var _locationList=MutableLiveData<List<LocationModel>>()
    val locationList:LiveData<List<LocationModel>>
        get()=_locationList


    fun insertLocation(location:LocationModel){
        errorMessage=null
        _errorData.value=false
        _success.value=false
        try{
            job=CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            repo.insertLocation(location)
                withContext(Dispatchers.Main){
                    _success.value=true
                }
            }
        }catch (e:Exception){
            errorMessage=e.localizedMessage
            _errorData.value=true
        }

    }

    fun loadLocations() {
        errorMessage=null
        _errorData.value=false
        _success.value=false
        try {
            job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                var response=repo.getLocation()
                withContext(Dispatchers.Main){
                    _locationList.value=response
                    _success.value=true
                }
            }
        }catch (e:Exception){
            errorMessage=e.localizedMessage
            _errorData.value=true
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}