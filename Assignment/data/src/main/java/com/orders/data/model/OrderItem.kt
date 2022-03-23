package com.orders.data.model

data class OrderItem(
    val id: Long? = null,
    val type: String? = null,
    val size: String? = null,
    val topping: List<String>? = null,
    val sauce: List<String>? = null
)