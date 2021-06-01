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
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ChatsFragment : Fragment() {
    private val binding by lazy{
        ChatsFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var chatViewModel: ChatViewModel;
    private lateinit var userViewModel: UserViewModel;

    private lateinit var chatListAdapter : ChatListAdapter;

    private val onItemSelect : (Chat) -> Unit = {
            selected ->
        //TDOD получение бандла в фрагмент назначения
        val bundle : Bundle = Bundle();
        bundle.putString("chatid",selected.uuid.toString())
        bundle.putString("managername",selected.manager.name)
        bundle.putString("userid", selected.client.uuid.toString())
        findNavController().navigate(R.id.action_nav_chats_fragment_to_nav_chat_fragment, bundle)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chatViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ChatViewModel::class.java)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        //Recycler view
        chatListAdapter = ChatListAdapter(onItemSelect);
        binding.chatslistrv.adapter = chatListAdapter;
        binding.chatslistrv.layoutManager = LinearLayoutManager(context)

        //VM Observer
        chatViewModel.chats.observe(viewLifecycleOwner, Observer{
            if(it.isSuccess && it.entity != null)
                updateChats(it.entity);
            else
                Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()

        })

        if(userViewModel.userLD.value == null) {
            Toast.makeText(requireContext(), getString(R.string.not_authorized), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_login_fragment)
        }else{
            chatViewModel.loadChats(userViewModel.userLD.value!!.uuid)
        }
        return binding.root;
    }

    private fun updateChats(chatsToDisplay:List<Chat>){
        if(chatsToDisplay.isNotEmpty()){
            binding.createNewChatGroup.visibility = View.GONE;
            chatListAdapter.setChats(chatsToDisplay);
        }
        else{
            /*Если нет чатов, то у пользователя есть возможность создать 1 чат с рандомным менеджером. для того, чтобы пользователь мог задать вопрос */
            chatListAdapter.setChats(emptyList())
            binding.createNewChatGroup.visibility = View.VISIBLE;
            binding.createChatButton.setOnClickListener{
                binding.createChatButton.isEnabled = false;
                chatViewModel.autoCreateChat(userViewModel.userLD.value!!.uuid)
                chatViewModel.isChatCreatedSLD.observe(viewLifecycleOwner, Observer{
                    //Новый чат создан
                    if( !(it.isSuccess && it.entity != null))
                        Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    binding.createChatButton.isEnabled = true;
                    binding.createNewChatGroup.visibility = View.GONE;
                    chatViewModel.loadChats(userViewModel.userLD.value!!.uuid) //Обновляем чаты
                })
            }
        }

    }
}