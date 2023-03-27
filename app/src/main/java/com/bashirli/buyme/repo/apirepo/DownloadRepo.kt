package com.bashirli.buyme.repo.apirepo

import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.service.Service
import com.bashirli.buyme.util.Resource
import javax.inject.Inject

class DownloadRepo @Inject constructor(
    private var service:Service
) : Repo {

    override suspend fun getProducts(): Resource<List<ProductData>> {
        return try{
            var response=service.getProducts()
            if(response.isSuccessful){
                response.body()?.let {
                     Resource.success(it)
                }?:  Resource.error("Loading failed!",null)

            }else{
                 Resource.error("Loading failed!",null)
            }
        }catch (e:Exception){
             Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getSingleProduct(id: Int): Resource<ProductData> {
        return try{
            var response=service.getSingleProduct(id)
            if(response.isSuccessful){
                response.body()?.let {
                     Resource.success(it)
                }?:  Resource.error("Loading failed!",null)

            }else{
                 Resource.error("Loading failed!",null)
            }
        }catch (e:Exception){
             Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getOtherProduct(): Resource<List<ProductData>> {
        return try{
            var response=service.getOtherProduct()
            if(response.isSuccessful){
                response.body()?.let {
                     Resource.success(it)
                }?:  Resource.error("Loading failed!",null)

            }else{
                 Resource.error("Loading failed!",null)
            }
        }catch (e:Exception){
             Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getSearchedProduct(search: String): Resource<List<ProductData>> {
        return try{
            var response=service.getSearchedProduct(search)
            if(response.isSuccessful){
                response.body()?.let {
                      Resource.success(it)
                }?:  Resource.error("Error",null)
            }else{
                return  Resource.error("Error",null)
            }
        }catch (e:Exception){
              Resource.error(e.localizedMessage,null)
        }
    }


}