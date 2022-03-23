package com.orders.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.orders.database.db.OrdersDatabase
import com.orders.database.db.order.OrderDao
import com.orders.database.db.ordersauce.OrderSauceDao
import com.orders.database.db.ordertopping.OrderToppingDao
import com.orders.database.repository.RoomDBRepository

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideOrderDao(@ApplicationContext context: Context): OrderDao {
        return OrdersDatabase.getInstance(context).orderDao
    }

    @Provides
    fun provideOrderToppingDao(@ApplicationContext context: Context): OrderToppingDao {
        return OrdersDatabase.getInstance(context).orderToppingDao
    }

    @Provides
    fun provideOrderSauceDao(@ApplicationContext context: Context): OrderSauceDao {
        return OrdersDatabase.getInstance(context).orderSauceDao
    }

    @Provides
    fun provideRoomDBRepository(
        orderDao: OrderDao,
        orderToppingDao: OrderToppingDao,
        orderSauceDao: OrderSauceDao
    ) = RoomDBRepository(
        orderDao = orderDao,
        orderToppingDao = orderToppingDao,
        orderSauceDao = orderSauceDao
    )
}
