package com.orders.database.db.ordersauce

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_sauce")
data class OrderSauce(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val name: String
)