package com.orders.assignment.ui.fragment

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orders.data.model.OrderItem
import com.orders.data.model.networkmodel.OrderResponse
import com.orders.database.db.order.Order
import com.orders.database.db.ordersauce.OrderSauce
import com.orders.database.db.ordertopping.OrderTopping
import com.orders.database.repository.RoomDBRepository
import com.orders.network.remote.Repository
import com.orders.network.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: Repository,
    private val roomDBRepository: RoomDBRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<List<OrderItem>> =
        MutableLiveData()
    val response: LiveData<List<OrderItem>> = _response

    private val _loading: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    private val _amount: MutableLiveData<Double> = MutableLiveData()
    val amount: LiveData<Double> = _amount

    fun fetchOrders() = viewModelScope.launch {
        showLoading()
        repository.getOrders(getApplication()).collect { values ->
            hideLoading()
            _response.value = values.data?.getOrderItemList()
            saveOrdersToDatabase(values)
            calculatePrice()
        }
    }

    private fun saveOrdersToDatabase(values: NetworkResult<OrderResponse>) {
        if (values is NetworkResult.Success) {
            viewModelScope.launch {
                roomDBRepository.deleteAllOrders()
                roomDBRepository.deleteAllOrderTopping()
                roomDBRepository.deleteAllOrderSauce()
                values.data?.orders?.forEach {
                    val orderId = roomDBRepository.insertOrder(
                        Order(
                            type = it.type.orEmpty(),
                            size = it.size.orEmpty()
                        )
                    )

                    it.topping?.map { topping ->
                        OrderTopping(
                            orderId = orderId,
                            name = topping
                        )
                    }?.let { orderToppings ->
                        roomDBRepository.insertOrderToppings(orderToppings)
                    }

                    it.sauce?.map { sauce ->
                        OrderSauce(
                            orderId = orderId,
                            name = sauce
                        )
                    }?.let { orderSauces ->
                        roomDBRepository.insertOrderSauces(orderSauces)
                    }

                }
            }
        }
    }

    fun fetchOrdersFromDatabase() = viewModelScope.launch {
        showLoading()
        _response.value = roomDBRepository.getAllOrders().map {
            OrderItem(
                id = it.id,
                type = it.type,
                size = it.size,
                topping = roomDBRepository.getAllOrderTopping().map { topping -> topping.name },
                sauce = roomDBRepository.getAllOrderSauce().map { sauce -> sauce.name }
            )
        }
        calculatePrice()
        hideLoading()
    }

    private fun showLoading() {
        _loading.postValue(View.VISIBLE)
    }

    private fun hideLoading() {
        _loading.postValue(View.GONE)
    }

    private fun calculatePrice() {
        var totalAmount = 0.0
        _response.value?.forEach {
            totalAmount += when (it.size?.lowercase()) {
                "small" -> 4
                "medium" -> 8
                "large" -> 15
                else -> 4
            }
        }
        _amount.value = totalAmount
    }
}