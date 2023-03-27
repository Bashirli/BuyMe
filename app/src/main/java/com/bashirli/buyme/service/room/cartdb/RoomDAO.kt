package com.bashirli.buyme.service.room.cartdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bashirli.buyme.model.Cart

@Dao
interface RoomDAO {

    @Insert
    fun insertData(cart: Cart)

    @Delete
    fun deleteData(cart: Cart)

    @Query("SELECT * From Cart")
    fun getAllData():List<Cart>

    @Query("delete from cart")
    fun deleteAllData()

    @Query("delete from cart where ProductID=:id")
    fun deleteSelected(id:Int)


}