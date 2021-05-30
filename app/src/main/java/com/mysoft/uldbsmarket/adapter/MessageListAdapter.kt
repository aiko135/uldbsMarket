package com.mysoft.uldbsmarket.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Message


class MessageListAdapter(private var currentUserUuid : String) : RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>()  {
    private var messages: List<Message> = ArrayList();

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.findViewById(R.id.chat_mess);
        val cardView : CardView = itemView.findViewById(R.id.card_view_mess)
        val constraint : ConstraintLayout = itemView.findViewById(R.id.constraint_mess)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_chat_elem, parent, false)
        val viewHolder = MessageListAdapter.MessageViewHolder(view)
        return viewHolder;
    }

    override fun getItemCount(): Int {
        return messages.size;
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMess = messages[position];
        holder.message.text = currentMess.text;

        val constraintSet = ConstraintSet()
        constraintSet.clone(holder.constraint)
        if(currentMess.user == currentUserUuid){
            constraintSet.connect(R.id.card_view_mess,ConstraintSet.END,R.id.constraint_mess,ConstraintSet.END,0);
            holder.message.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            holder.message.setTextColor(Color.parseColor("#ffffff"))
            holder.cardView.setBackgroundResource(R.color.colorPrimary)
            holder.cardView.alpha = 1f

        }else{
            holder.message.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            constraintSet.connect(R.id.card_view_mess,ConstraintSet.START,R.id.constraint_mess,ConstraintSet.START,0);
            holder.cardView.alpha = 1f
        }
        constraintSet.applyTo(holder.constraint);

    }

    fun setMessagees(newMessList  : List<Message>){
        messages = newMessList;
        notifyDataSetChanged();
    }
}