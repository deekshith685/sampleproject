package com.orders.network.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ordersService: OrdersService
){
    suspend fun getAllOrders() = ordersService.getAllOrders()
}