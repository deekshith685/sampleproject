package com.orders.assignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orders.assignment.R
import com.orders.assignment.adapter.recyclerviewadapter.OrdersRecyclerViewAdapter
import com.orders.assignment.databinding.FragmentOrderBinding
import com.orders.assignment.listener.OnRecyclerViewItemClick
import com.orders.data.model.OrderItem
import com.orders.network.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), OnRecyclerViewItemClick {

    private val orderViewModel by viewModels<OrderViewModel>()
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val orderList = mutableListOf<OrderItem>()
    private val adapter = OrdersRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = orderViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        // no-op
    }

    private fun initViews() {
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            orderViewModel.fetchOrders()
        } else {
            orderViewModel.fetchOrdersFromDatabase()
        }
        binding.rvOrders.adapter = adapter.apply {
            setData(orderList)
        }

        binding.btnPay.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "TODO: proceed to next screen",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initObservers() {
        orderViewModel.response.observe(viewLifecycleOwner) {
            orderList.clear()
            orderList.addAll(it)
            adapter.setData(orderList)
        }
        orderViewModel.amount.observe(viewLifecycleOwner) {
            binding.btnPay.text = getString(R.string.proceed_to_pay, it.toString())
        }
    }
}