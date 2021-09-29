package com.mysoft.uldbsmarket.fragment

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
import com.mysoft.uldbsmarket.adapter.OrderListAdapter
import com.mysoft.uldbsmarket.databinding.MyordersFragmentBinding
import com.mysoft.uldbsmarket.model.dto.RequestError
import com.mysoft.uldbsmarket.model.dto.RequestSuccess
import com.mysoft.uldbsmarket.vm.*
import java.util.*

class MyOrdersFragment : Fragment() {
    private val binding by lazy{
        MyordersFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var requestViewModel: RequestViewModel
    private lateinit var userViewModel: UserViewModel;
    private lateinit var chatViewModel: ChatViewModel;

    private lateinit var orderListAdapter:OrderListAdapter

    private val onButtonCreateChatPress : (Button, UUID)->Unit = {
        buttonPressed: Button, managerUuid:UUID ->
        buttonPressed.isEnabled = false;
        chatViewModel.createChat(userViewModel.userLD.value!!.uuid, managerUuid)
        chatViewModel.isChatCreatedSLD.observe(viewLifecycleOwner, Observer{
            if(it is RequestError)
                Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
            buttonPressed.isEnabled = true;
            findNavController().navigate(R.id.nav_chats_fragment);
        })
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
        chatViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ChatViewModel::class.java)

        //Recycler view
        orderListAdapter = OrderListAdapter(requireContext(),onButtonCreateChatPress);
        binding.orderslistrv.adapter = orderListAdapter;
        binding.orderslistrv.layoutManager = LinearLayoutManager(context)

        //Data update observer
        requestViewModel.myOrdersLD.observe(viewLifecycleOwner, Observer {
            when (it){
                is RequestSuccess -> orderListAdapter.setOrders(it.entity)
                is RequestError -> Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })

        if(userViewModel.userLD.value == null) {
            Toast.makeText(requireContext(), getString(R.string.not_authorized), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_login_fragment)
        }else
            requestViewModel.getOrders(userViewModel.userLD.value!!.uuid, false)

        return binding.root;
    }
}