package com.bashirli.buyme.service.room.favoritesdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bashirli.buyme.model.FavoritesModel

@Dao
interface FavoritesDAO {

    @Insert
    fun insertData(favoritesModel: FavoritesModel)

    @Query("Delete from Favorites where productId=:id")
    fun deleteData(id:Int)

    @Query("select * from Favorites")
    fun getAllData():List<FavoritesModel>

}