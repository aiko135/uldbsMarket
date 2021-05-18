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
import com.mysoft.uldbsmarket.databinding.ChatsFragmentBinding
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.vm.ChatViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ChatsFragment : Fragment() {
    private lateinit var binding: ChatsFragmentBinding
    private lateinit var chatViewModel: ChatViewModel;

    private lateinit var chatListAdapter : ChatListAdapter;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.chats_fragment, container, false)
        binding = ChatsFragmentBinding.inflate(inflater)

        chatViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ChatViewModel::class.java)

        //Recycler view
        chatListAdapter = ChatListAdapter(onItemSelect);
        binding.chatslistrv.adapter = chatListAdapter;
        binding.chatslistrv.layoutManager = LinearLayoutManager(context)

        //VM Observer
        chatViewModel.chats.observe(viewLifecycleOwner, Observer{ updated : List<Chat> ->
            chatListAdapter.setChats(updated);
        })
        chatViewModel.user.observe(viewLifecycleOwner, Observer{
            onUserFindResult.invoke(it);
        })

        //Ищем пользователя
        chatViewModel.readUserInfo()

        return binding.root;
    }
    //Поиск пользователя в преференциях завершен
    private val onUserFindResult : (User?)->Unit = {
        result : User? ->
        if(result == null || chatViewModel.user.value == null){
            //Пользователь не авторизирован
            binding.textView13.text = getString(R.string.not_authorized);
        }
        else{
            //Пользователь авторизован
            chatViewModel.loadChats(chatViewModel.user.value!!.uuid) {
                //Ошибка загрузки
                requireActivity().runOnUiThread {
                    Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT).show()
                }
            };
        }
    }

    private val onItemSelect : (Chat) -> Unit = {
        selected ->
        //TDOD получение бандла в фрагмент назначения
        val bundle : Bundle = Bundle();
        bundle.putString("chatid",selected.uuid)
        bundle.putString("managername",selected.managerUuid.name)
        bundle.putString("userid", selected.clientUuid.uuid)
        findNavController().navigate(R.id.action_nav_chats_fragment_to_nav_chat_fragment, bundle)
    }

}