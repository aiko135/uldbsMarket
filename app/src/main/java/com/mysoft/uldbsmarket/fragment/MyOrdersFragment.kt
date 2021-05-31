package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.adapter.ChatListAdapter
import com.mysoft.uldbsmarket.adapter.OrderListAdapter
import com.mysoft.uldbsmarket.databinding.ItemCatalogFragmentBinding
import com.mysoft.uldbsmarket.databinding.MyordersFragmentBinding
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.RequestViewModel
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class MyOrdersFragment : Fragment() {
    private val binding by lazy{
        MyordersFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var requestViewModel: RequestViewModel
    private lateinit var userViewModel: UserViewModel;

    private lateinit var orderListAdapter:OrderListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requestViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(RequestViewModel::class.java)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        //Recycler view
        orderListAdapter = OrderListAdapter(requireContext());
        binding.orderslistrv.adapter = orderListAdapter;
        binding.orderslistrv.layoutManager = LinearLayoutManager(context)

        //Data update observer
        requestViewModel.myOrdersLD.observe(viewLifecycleOwner, Observer {
            if(it.isSuccess && it.entity != null)
                orderListAdapter.setOrders(it.entity)
            else
                Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
        })

        if(userViewModel.userLD.value == null) {
            Toast.makeText(requireContext(), getString(R.string.not_authorized), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_login_fragment)
        }else
            requestViewModel.getMyOrders(userViewModel.userLD.value!!.uuid)

        return binding.root;
    }
}