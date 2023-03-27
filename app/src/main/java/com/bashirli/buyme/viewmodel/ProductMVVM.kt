package com.bashirli.buyme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashirli.buyme.model.Cart
import com.bashirli.buyme.model.FavoritesModel
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.repo.roomrepo.cart.DbRepo
import com.bashirli.buyme.repo.roomrepo.favorites.FavoritesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProductMVVM @Inject constructor(
    var repo: DbRepo,
    var favRepo:FavoritesRepo
) :ViewModel() {

     private var _isLiked=MutableLiveData<Boolean>()
     val isLiked:LiveData<Boolean>
     get()=_isLiked

     private var _errorData=MutableLiveData<Boolean>()
     val errorData:LiveData<Boolean>
     get()= _errorData

     private var _success=MutableLiveData<Boolean>()
     val success:LiveData<Boolean>
          get()= _success

     var errorMessage:String?=null

     private var job: Job?=null

     private var exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->

          errorMessage=throwable.localizedMessage
          _errorData.value=true
     }

     fun addToFavorites(productData: ProductData){
          try {
          job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch{
               favRepo.insertData(FavoritesModel(productData.id))
          }
          }catch (e:Exception){}

     }

     fun deleteFromFavorites(productData: ProductData){
          try {
               job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch{
                    favRepo.deleteData(FavoritesModel(productData.id))
               }
          }catch (e:Exception){
               println(e.localizedMessage)
          }
     }

     fun loadFav(productData: ProductData){
          try {
               job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                    var list=favRepo.getAllData()
                    withContext(Dispatchers.Main){
                         println(list)
                         for (data in list){
                              if(data.productId==productData.id){
                                   _isLiked.value=true
                                   break
                              }else{
                                   _isLiked.value=false
                              }
                         }

                    }
               }
          }catch (e:Exception){}
     }

     fun addToCart(cart: Cart){
          _errorData.value=false
          _success.value=false
          errorMessage=null
          try{
               job=CoroutineScope(Dispatchers.IO+exceptionHandler)
                    .launch {
                    repo.insertData(cart)
                         withContext(Dispatchers.Main){
                              _success.value=true
                         }
               }
          }catch (e:Exception){
               errorMessage=e.localizedMessage
               _errorData.value=true
          }
     }

     override fun onCleared() {
          job?.cancel()
          super.onCleared()

     }


}