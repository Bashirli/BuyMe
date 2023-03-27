package com.bashirli.buyme.model
import java.io.Serializable
data class ResponseCategory (
    val id:Int,
    val name:String,
    val image:String,
    val creationAt:String,
    val updatedAt:String
) :Serializable