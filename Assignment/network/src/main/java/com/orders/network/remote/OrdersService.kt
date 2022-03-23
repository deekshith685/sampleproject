package com.orders.network.remote

import com.orders.network.utils.Constants
import com.orders.data.model.networkmodel.OrderResponse
import retrofit2.Response
import retrofit2.http.GET

interface OrdersService {

    @GET(Constants.ORDERS)
    suspend fun getAllOrders(): Response<OrderResponse>
}