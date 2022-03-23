package com.orders.assignment.adapter.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orders.assignment.databinding.LayoutRecyclerViewItemOrderBinding
import com.orders.assignment.extension.listen
import com.orders.assignment.listener.OnRecyclerViewItemClick
import com.orders.data.model.OrderItem

class OrdersRecyclerViewAdapter(private val onRecyclerViewItemClick: OnRecyclerViewItemClick?) :
    RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrdersViewHolder>() {

    private var orderListItems = mutableListOf<OrderItem>()

    inner class OrdersViewHolder(val binding: LayoutRecyclerViewItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            LayoutRecyclerViewItemOrderBinding.inflate(inflater, parent, false)
        return OrdersViewHolder(binding).listen { position, _ ->
            onRecyclerViewItemClick?.onItemClick(position)
        }
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.binding.tvHeading.text = orderListItems[position].type.orEmpty()
        holder.binding.tvSize.text = orderListItems[position].size.orEmpty()
    }

    override fun getItemCount() = orderListItems.size

    fun setData(orderItems: List<OrderItem>) {
        this.orderListItems.clear()
        this.orderListItems.addAll(orderItems)
        notifyDataSetChanged()
    }
}