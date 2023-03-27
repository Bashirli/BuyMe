package com.bashirli.buyme.repo.roomrepo.cart

import com.bashirli.buyme.model.Cart
import com.bashirli.buyme.service.room.cartdb.RoomDAO
import javax.inject.Inject

class DatabaseRepo @Inject constructor(
     var roomDAO: RoomDAO
): DbRepo {

    override suspend fun insertData(cart: Cart) {
        roomDAO.insertData(cart)
    }

    override suspend fun deleteData(cart: Cart){
        roomDAO.deleteData(cart)
    }

    override suspend fun getAllData() : List<Cart>{
       return roomDAO.getAllData()
    }

    override suspend fun deleteAllData() {
        roomDAO.deleteAllData()
    }

    override suspend fun deleteSelected(id: Int) {
        roomDAO.deleteSelected(id)
    }


}