package com.bashirli.buyme.repo.roomrepo.location

import com.bashirli.buyme.model.LocationModel

interface LocationRepo {
    suspend fun getLocation():List<LocationModel>
    suspend fun insertLocation(locationModel: LocationModel)
}