package com.bashirli.buyme.repo.roomrepo.cart

import com.bashirli.buyme.model.Cart

interface DbRepo {
    suspend fun insertData(cart: Cart)

    suspend fun deleteData(cart: Cart)

    suspend fun getAllData():List<Cart>

    suspend fun deleteAllData()

    suspend fun deleteSelected(id:Int)
}