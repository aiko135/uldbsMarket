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

class ChatFragment : Fragment() {
    private val binding by lazy{
        ChatFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var chatViewModel : ChatViewModel;

    private lateinit var messageListAdapter : MessageListAdapter;

    //TODO refactor
    private val onRequestError = {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), R.string.request_err, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chatViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ChatViewModel::class.java)


        binding.contactorName.text  = arguments?.getString("managername");
        chatViewModel.chatid = arguments?.getString("chatid");
        chatViewModel.userid = arguments?.getString("userid");

        //Recycler view
        var lm  = LinearLayoutManager(context);
       // lm.reverseLayout = true;
        lm.stackFromEnd = true
        binding.recyclerChat.layoutManager = lm
        messageListAdapter = MessageListAdapter(chatViewModel.userid!!);
        binding.recyclerChat.adapter =  messageListAdapter;

        //Observer
        chatViewModel.messages.observe(viewLifecycleOwner, Observer {
            messageListAdapter.setMessagees(it)
        })

        chatViewModel.loadMessages(onRequestError)
        return binding.root;
    }
}