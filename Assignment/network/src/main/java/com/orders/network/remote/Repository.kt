package com.orders.network.remote

import android.content.Context
import com.orders.data.model.networkmodel.OrderResponse
import com.orders.mock.MocksProvider
import com.orders.network.BuildConfig
import com.orders.network.model.BaseApiResponse
import com.orders.network.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {
    suspend fun getOrders(context: Context): Flow<NetworkResult<OrderResponse>> {
        return flow {
            if (BuildConfig.ENVIRONMENT == "NETWORK") {
                emit(safeApiCall { remoteDataSource.getAllOrders() })
            } else {
                emit(NetworkResult.Success(MocksProvider.getAllOrders(context)))
            }
        }.flowOn(Dispatchers.IO)
    }
}