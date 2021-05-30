package com.mysoft.uldbsmarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Chat

class ChatListAdapter(private val onSelectListener : (c: Chat)->Unit )
    : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    private var chats: List<Chat> = ArrayList();

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.chat_user_name);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_chats_elem, parent, false)
        val viewHolder = ChatViewHolder(view)
        view.setOnClickListener{
            onSelectListener.invoke(chats[viewHolder.adapterPosition])
        }
        return viewHolder;
    }

    override fun getItemCount(): Int {
        return chats.size;
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentChat = chats[position];
        holder.nameTV.text = currentChat.manager.name;
    }

    fun setChats(newChatList : List<Chat>){
        chats = newChatList
        notifyDataSetChanged()
    }

}