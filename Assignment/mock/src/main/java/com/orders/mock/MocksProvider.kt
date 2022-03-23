package com.orders.mock

import android.content.Context
import com.google.gson.Gson
import com.orders.data.model.networkmodel.OrderResponse

object MocksProvider {

    fun getAllOrders(context: Context): OrderResponse = Gson().fromJson(
        readJson(context, "orders.json"),
        OrderResponse::class.java
    )

    private fun readJson(context: Context, fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }
}