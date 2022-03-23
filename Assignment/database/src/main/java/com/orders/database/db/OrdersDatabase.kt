package com.orders.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orders.database.db.order.Order
import com.orders.database.db.order.OrderDao
import com.orders.database.db.ordersauce.OrderSauce
import com.orders.database.db.ordersauce.OrderSauceDao
import com.orders.database.db.ordertopping.OrderTopping
import com.orders.database.db.ordertopping.OrderToppingDao

@Database(
    entities = [Order::class, OrderSauce::class, OrderTopping::class],
    version = 1,
    exportSchema = false
)
abstract class OrdersDatabase : RoomDatabase() {

    abstract val orderDao: OrderDao
    abstract val orderSauceDao: OrderSauceDao
    abstract val orderToppingDao: OrderToppingDao

    companion object {

        @Volatile
        private var instance: OrdersDatabase? = null

        fun getInstance(context: Context): OrdersDatabase {
            synchronized(this) {
                var mInstance = instance

                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(
                        context.applicationContext,
                        OrdersDatabase::class.java,
                        "orders_database"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    instance = mInstance
                }
                return mInstance
            }
        }
    }
}
