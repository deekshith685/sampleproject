package com.orders.database.db.ordertopping

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderToppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(order: List<OrderTopping>)

    @Query("SELECT * FROM order_topping ORDER BY id ASC")
    fun getAll(): List<OrderTopping>

    @Query("SELECT * FROM order_topping WHERE id = :id")
    fun get(id: Long): OrderTopping

    @Query("SELECT * FROM order_topping WHERE orderId = :orderId")
    fun getByOrderId(orderId: Long): List<OrderTopping>

    @Query("DELETE FROM order_topping")
    fun deleteRecords()
}