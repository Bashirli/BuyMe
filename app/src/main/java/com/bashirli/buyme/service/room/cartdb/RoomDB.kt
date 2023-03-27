package com.bashirli.buyme.service.room.cartdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashirli.buyme.model.Cart


@Database(entities = [Cart::class], version = 1)
abstract class  RoomDB :RoomDatabase() {
    abstract fun getDao(): RoomDAO
}