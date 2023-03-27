package com.bashirli.buyme.service.room.favoritesdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashirli.buyme.model.FavoritesModel

@Database(entities = [FavoritesModel::class], version = 1)
abstract class FavoritesDB : RoomDatabase() {
    abstract fun getDao():FavoritesDAO
}