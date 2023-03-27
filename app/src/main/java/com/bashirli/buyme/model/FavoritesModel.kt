package com.bashirli.buyme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class FavoritesModel(
    @ColumnInfo(name = "productId") val productId:Int,

    @PrimaryKey(autoGenerate = true) val id:Int?=null
) {
}