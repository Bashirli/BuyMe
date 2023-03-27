package com.bashirli.buyme.repo.roomrepo.location

import com.bashirli.buyme.model.LocationModel
import com.bashirli.buyme.service.room.locationdb.LocationDAO
import javax.inject.Inject

class LocRepo @Inject constructor(
    var locationDAO: LocationDAO
) : LocationRepo {

    override suspend fun getLocation(): List<LocationModel> {
        return locationDAO.getLocations()
    }

    override suspend fun insertLocation(locationModel: LocationModel) {
        locationDAO.insertLocation(locationModel)
    }
}