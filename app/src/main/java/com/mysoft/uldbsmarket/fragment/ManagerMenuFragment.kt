package com.mysoft.uldbsmarket.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.adapter.ManagerOrderListAdapter
import com.mysoft.uldbsmarket.adapter.OrderListAdapter
import com.mysoft.uldbsmarket.databinding.ManagerMenuFragmentBinding
import com.mysoft.uldbsmarket.model.dto.RequestError
import com.mysoft.uldbsmarket.model.dto.RequestSuccess
import com.mysoft.uldbsmarket.vm.RequestViewModel
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import java.util.*


class ManagerMenuFragment : Fragment() {
    private val binding by lazy{
        ManagerMenuFragmentBinding.inflate(layoutInflater);
    }

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var orderListAdapter: ManagerOrderListAdapter
    
    private val onClickGoToChat: (Button, UUID)->Unit = {
        button, uuid ->  
    }

    private val onClickHideShowFinished : View.OnClickListener = View.OnClickListener {
        //TODO ---------------------------
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requestViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(RequestViewModel::class.java)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)
        binding.hideshow.setOnClickListener(onClickHideShowFinished)

        //Recycler view
        orderListAdapter = ManagerOrderListAdapter(requireContext(),onClickGoToChat);
        binding.orderslistrv.adapter = orderListAdapter;
        binding.orderslistrv.layoutManager = LinearLayoutManager(context)

        if(userViewModel.userLD.value == null) {
            Toast.makeText(requireContext(), getString(R.string.not_authorized), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_login_fragment)
        }else{
            requestViewModel.allStatusLD.observe(viewLifecycleOwner, Observer {
                when (it){
                    is RequestSuccess -> loadOrders()
                    is RequestError -> Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            })
            requestViewModel.loadAllStatusList();
        }

        return binding.root;
    }

    private fun loadOrders(){
        requestViewModel.myOrdersLD.observe(viewLifecycleOwner, Observer {
            when (it){
                is RequestSuccess -> orderListAdapter.setOrders(it.entity)
                is RequestError -> Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })
        requestViewModel.getOrders(userViewModel.userLD.value!!.uuid, true)
    }
}