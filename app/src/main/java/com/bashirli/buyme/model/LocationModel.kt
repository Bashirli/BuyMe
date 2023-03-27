package com.bashirli.buyme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationModel(
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name="lat") var lat:Double,
    @ColumnInfo(name="longit") var longit:Double,
    @PrimaryKey(autoGenerate = true) val id:Int?=null
)