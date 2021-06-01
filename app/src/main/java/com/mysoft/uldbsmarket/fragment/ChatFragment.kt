package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.adapter.MessageListAdapter
import com.mysoft.uldbsmarket.databinding.ChatFragmentBinding
import com.mysoft.uldbsmarket.databinding.ChatsFragmentBinding
import com.mysoft.uldbsmarket.vm.ChatViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import java.util.*

class ChatFragment : Fragment() {
    private val binding by lazy{
        ChatFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var chatViewModel : ChatViewModel;

    private lateinit var messageListAdapter : MessageListAdapter;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chatViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ChatViewModel::class.java)

        binding.contactorName.text  = arguments?.getString("managername");
        val chatid = arguments?.getString("chatid");
        val userid = arguments?.getString("userid");

        if(chatid != null && userid != null){
            //Recycler view
            val lm  = LinearLayoutManager(context);
            lm.stackFromEnd = true
            binding.recyclerChat.layoutManager = lm
            messageListAdapter = MessageListAdapter(userid!!);
            binding.recyclerChat.adapter =  messageListAdapter;

            //Observer
            chatViewModel.messages.observe(viewLifecycleOwner, Observer {
                if(it.isSuccess && it.entity != null)
                    messageListAdapter.setMessagees(it.entity)
                else
                    Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()

            })

            chatViewModel.loadMessages(UUID.fromString(chatid))
        }
        return binding.root;
    }
}