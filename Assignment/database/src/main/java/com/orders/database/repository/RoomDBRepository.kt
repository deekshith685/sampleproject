package com.orders.database.repository

import com.orders.database.db.order.Order
import com.orders.database.db.order.OrderDao
import com.orders.database.db.ordersauce.OrderSauce
import com.orders.database.db.ordersauce.OrderSauceDao
import com.orders.database.db.ordertopping.OrderTopping
import com.orders.database.db.ordertopping.OrderToppingDao
import javax.inject.Inject

class RoomDBRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val orderSauceDao: OrderSauceDao,
    private val orderToppingDao: OrderToppingDao
) {
    fun deleteAllOrders() = orderDao.deleteRecords()
    fun getAllOrders() = orderDao.getAll()
    suspend fun insertOrder(order: Order) = orderDao.insert(order)

    fun deleteAllOrderSauce() = orderSauceDao.deleteRecords()
    fun getAllOrderSauce() = orderSauceDao.getAll()
    suspend fun insertOrderSauces(orderSauces: List<OrderSauce>) =
        orderSauceDao.insertAll(orderSauces)

    fun deleteAllOrderTopping() = orderToppingDao.deleteRecords()
    fun getAllOrderTopping() = orderToppingDao.getAll()
    suspend fun insertOrderToppings(orderToppings: List<OrderTopping>) =
        orderToppingDao.insertAll(orderToppings)
}