package com.bashirli.buyme.model
import java.io.Serializable
data class ProductData(
    val id:Int,
    val title:String,
    val price:String,
    val description:String,
    val images:List<String>,
    val creationAt:String,
    val updatedAt:String,
    val category: ResponseCategory
):Serializable