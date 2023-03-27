package com.bashirli.buyme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.buyme.model.CartModel
import com.bashirli.buyme.model.Cart
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.repo.apirepo.Repo
import com.bashirli.buyme.repo.roomrepo.cart.DbRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CartMVVM @Inject constructor(
    private var roomRepo: DbRepo,
    private var apiRepo:Repo
) : ViewModel() {

    private var _errorData= MutableLiveData<Boolean>()
    val errorData: LiveData<Boolean>
        get()= _errorData

    private var _success= MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get()= _success

    private var _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
    get()=_loading

    var errorMessage:String?=null

    private var job: Job?=null

    private var exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        _loading.value=false
        errorMessage=throwable.localizedMessage
        _errorData.value=true
    }

    private var dataFromApi=MutableLiveData<List<ProductData>>()

    private var _favoritesData=MutableLiveData<List<CartModel>>()
    val favoriteData:LiveData<List<CartModel>>
    get() = _favoritesData




    private fun selectFavoritesData(apiList:List<ProductData>,dataFromDB:List<Cart>){
        val favoriteList=ArrayList<CartModel>()
        for(i in 0..dataFromDB.size-1){
            for(data in apiList){
                if(data.id==dataFromDB.get(i).productId){
                favoriteList.add(CartModel(data,dataFromDB.get(i).productCount))
                    break
                }
            }
        }

        _favoritesData.value=favoriteList
        _loading.value=false
        _success.value=true
    }

    private fun loadFavorites(){
        try{
            dataFromApi.observeForever {apiList->
                job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                    var dataFromDB=roomRepo.getAllData()
                    withContext(Dispatchers.Main){
                        selectFavoritesData(apiList,dataFromDB)
                    }
                }
            }
        }catch (e:Exception){
            _loading.value=false
            errorMessage=e.localizedMessage
            _errorData.value=true
        }
    }



     fun loadData(){
        _loading.value=true
        _success.value=false
        _errorData.value=false
        errorMessage=null
        try{
            job=CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                var response= apiRepo.getProducts()
                withContext(Dispatchers.Main){
                    response.data?.let {
                        dataFromApi.value=it
                    }
                }
            }
            loadFavorites()
        }catch (e:Exception){
            _loading.value=false
            errorMessage=e.localizedMessage
            _errorData.value=true
        }
    }


    fun clearData(){
        try {
        job= CoroutineScope(Dispatchers.IO).launch {
            roomRepo.deleteAllData()
        }
        }catch (e:Exception){

        }
    }

    fun removeFromDB(position:Int){
        try {
            viewModelScope.launch(Dispatchers.IO) {
            roomRepo.deleteSelected(position)
            }
        }catch (e:Exception){

        }
    }

    fun insertData(cart: Cart){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                roomRepo.insertData(cart)
            }
        }catch (e:Exception){}
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}