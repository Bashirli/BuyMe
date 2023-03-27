package com.bashirli.buyme.service

import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.util.BODY_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET(BODY_URL+"products")
    suspend fun getProducts() : Response<List<ProductData>>

    @GET(BODY_URL+"products/")
    suspend fun getSingleProduct(@Query("id") id:Int):Response<ProductData>

    @GET(BODY_URL+"products/?categoryId=5")
    suspend fun getOtherProduct(): Response<List<ProductData>>

    @GET(BODY_URL+"products")
    suspend fun getSearchedProduct(@Query("title") search:String):Response<List<ProductData>>

}