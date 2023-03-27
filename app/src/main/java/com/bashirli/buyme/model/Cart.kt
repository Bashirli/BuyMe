package com.bashirli.buyme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class Cart(
    @ColumnInfo(name="ProductID") var productId:Int,
    @ColumnInfo(name = "ProductCount") var productCount:Int,
    @ColumnInfo(name = "ProductTitle") var productTitle:String,
    @PrimaryKey(autoGenerate = true) val id:Int?=null
)
