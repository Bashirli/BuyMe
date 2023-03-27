package com.bashirli.buyme.service.room.locationdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashirli.buyme.model.LocationModel

@Database(entities = [LocationModel::class], version = 1)
abstract class LocationDB :RoomDatabase() {
    abstract fun getDao() : LocationDAO
}