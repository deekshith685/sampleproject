package com.orders.database.db.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order): Long

    @Query("SELECT * FROM orders ORDER BY id ASC")
    fun getAll(): MutableList<Order>

    @Query("SELECT * FROM orders WHERE id = :id")
    fun get(id: Long): LiveData<Order>

    @Query("DELETE FROM orders")
    fun deleteRecords()
}