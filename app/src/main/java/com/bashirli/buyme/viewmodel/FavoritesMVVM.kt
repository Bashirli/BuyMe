package com.bashirli.buyme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashirli.buyme.model.FavoritesModel
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.repo.apirepo.Repo
import com.bashirli.buyme.repo.roomrepo.favorites.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FavoritesMVVM @Inject constructor(
    private var apiRepo:Repo,
    private var repo:FavoritesRepo
) : ViewModel() {
    private var _errorData= MutableLiveData<Boolean>()
    val errorData: LiveData<Boolean>
        get()= _errorData

    private var _success= MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get()= _success

    private var _loading= MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get()= _loading

    private var _favList= MutableLiveData<List<ProductData>>()
    val favList: LiveData<List<ProductData>>
        get()= _favList

    var errorMessage:String?=null

    private var job: Job?=null

    private var exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        _loading.value=false
        errorMessage=throwable.localizedMessage
        _errorData.value=true
    }

    init {
   loadData()
    }

    fun refreshPage(){
        loadData()
    }

   private  fun loadData(){
        _success.value=false
        _loading.value=true
        _errorData.value=false
        errorMessage=null
        try {
            job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                var response=apiRepo.getProducts()
                withContext(Dispatchers.Main){
                    response.data?.let {
                        loadFavorites(it)
                    }
                }
            }

        }catch (e:Exception){
            _loading.value=false
            errorMessage=e.localizedMessage
            _errorData.value=true
        }
    }


    private fun loadFavorites(myList:List<ProductData>){
        try {
            job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                var response=repo.getAllData()
                withContext(Dispatchers.Main){
                    val arrayList=ArrayList<ProductData>()
                    println(response)
                    for(data in myList){
                        for(secondData in response){
                            if(data.id==secondData.productId){
                                arrayList.add(data)
                            }
                        }
                    }
                    _favList.value=arrayList
                    _loading.value=false
                    _success.value=true
                }
            }

        }catch (e:Exception){
            _loading.value=false
            errorMessage=e.localizedMessage
            _errorData.value=true
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}