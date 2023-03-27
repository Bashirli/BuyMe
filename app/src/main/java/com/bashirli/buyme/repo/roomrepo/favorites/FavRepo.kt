package com.bashirli.buyme.repo.roomrepo.favorites

import com.bashirli.buyme.model.FavoritesModel
import com.bashirli.buyme.service.room.favoritesdb.FavoritesDAO
import javax.inject.Inject

class FavRepo @Inject constructor(
    private var service:FavoritesDAO
) : FavoritesRepo {
    override suspend fun insertData(favoritesModel: FavoritesModel) {
        service.insertData(favoritesModel)
    }

    override suspend fun deleteData(favoritesModel: FavoritesModel) {
        service.deleteData(favoritesModel.productId)
    }

    override suspend fun getAllData(): List<FavoritesModel> {
        return service.getAllData()
    }
}