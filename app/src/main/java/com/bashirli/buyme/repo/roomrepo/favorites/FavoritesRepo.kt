package com.bashirli.buyme.repo.roomrepo.favorites

import com.bashirli.buyme.model.FavoritesModel

interface FavoritesRepo {
    suspend fun insertData(favoritesModel: FavoritesModel)
    suspend fun deleteData(favoritesModel: FavoritesModel)
    suspend fun getAllData():List<FavoritesModel>
}