package com.orders.database.db.ordersauce

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderSauceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(order: List<OrderSauce>)

    @Query("SELECT * FROM order_sauce ORDER BY id ASC")
    fun getAll(): List<OrderSauce>

    @Query("SELECT * FROM order_sauce WHERE id = :id")
    fun get(id: Long): OrderSauce

    @Query("SELECT * FROM order_sauce WHERE orderId = :orderId")
    fun getByOrderId(orderId: Long): List<OrderSauce>

    @Query("DELETE FROM order_sauce")
    fun deleteRecords()
}