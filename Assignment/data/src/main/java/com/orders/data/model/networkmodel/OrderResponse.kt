package com.orders.data.model.networkmodel

import com.google.gson.annotations.SerializedName
import com.orders.data.model.OrderItem

data class OrderResponse(
    @SerializedName("order")
    val orders: List<Order>? = null
) {
    fun getOrderItemList() =
        orders?.map { it.toOrderItem() }
}

data class Order(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("size")
    val size: String? = null,
    @SerializedName("toppings")
    val topping: List<String>? = null,
    @SerializedName("sauce")
    val sauce: List<String>? = null
) {
    fun toOrderItem() =
        OrderItem(
            type = type,
            size = size,
            topping = topping,
            sauce = sauce
        )
}