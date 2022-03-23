package com.orders.database.db.ordertopping

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_topping")
data class OrderTopping(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val name: String
)