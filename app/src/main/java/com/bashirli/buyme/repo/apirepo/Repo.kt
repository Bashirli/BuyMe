package com.bashirli.buyme.repo.apirepo

import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.util.Resource

interface Repo {

    suspend fun getProducts() : Resource<List<ProductData>>

    suspend fun getSingleProduct(id:Int) : Resource<ProductData>

    suspend fun getOtherProduct() : Resource<List<ProductData>>

    suspend fun getSearchedProduct(search:String):Resource<List<ProductData>>

}