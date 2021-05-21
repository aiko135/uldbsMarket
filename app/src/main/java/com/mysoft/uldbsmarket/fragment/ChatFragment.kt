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
import com.mysoft.uldbsmarket.vm.ChatViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ChatFragment : Fragment() {
    private lateinit var binding: ChatFragmentBinding
    private lateinit var chatViewModel : ChatViewModel;

    private lateinit var messageListAdapter : MessageListAdapter;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ChatFragmentBinding.inflate(inflater)

        chatViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ChatViewModel::class.java)

        val chatUuid = arguments?.getString("chatid")
        val userUuid =  arguments?.getString("userid")
        val managerName=  arguments?.getString("managername")
        binding.contactorName.text  = managerName;

        chatViewModel.chatid = chatUuid;
        chatViewModel.userid = userUuid;

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

    private val onRequestError = {
        requireActivity().runOnUiThread {
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}