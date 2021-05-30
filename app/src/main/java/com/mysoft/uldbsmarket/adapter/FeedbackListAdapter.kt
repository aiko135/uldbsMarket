package com.mysoft.uldbsmarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Feedback


class FeedbackListAdapter() : RecyclerView.Adapter<FeedbackListAdapter.FeedbackViewHolder>() {
    private var feedbacks: List<Feedback> = ArrayList();

    class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username : TextView = itemView.findViewById(R.id.chat_user_name)
        val gradevalue : TextView = itemView.findViewById(R.id.grade_number_tv)
        val feedbacktext : TextView = itemView.findViewById(R.id.feedback_text_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_feedback_elem, parent, false)
        val viewHolder = FeedbackListAdapter.FeedbackViewHolder(view)
        return viewHolder;
    }

    override fun getItemCount(): Int {
        return feedbacks.size;
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val current = feedbacks[position]

        if(current.user != null){
            holder.username.text = current.user.name;
        }
        holder.gradevalue.text = current.grade.toString()
        if(current.feedback == null){
            holder.feedbacktext.text = "";
        }
        else{
            holder.feedbacktext.text = current.feedback;
        }
    }

    fun setFeedbacks(newFeedbacks: List<Feedback>) {
        feedbacks = newFeedbacks
        notifyDataSetChanged()
    }
}