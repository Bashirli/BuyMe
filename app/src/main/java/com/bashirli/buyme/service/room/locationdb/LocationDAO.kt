package com.bashirli.buyme.service.room.locationdb

import android.location.Location
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bashirli.buyme.model.LocationModel

@Dao
interface LocationDAO {

    @Query("Select * from location")
    fun getLocations():List<LocationModel>

    @Insert
    fun insertLocation(location:LocationModel)
}