package com.mysoft.uldbsmarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.dto.MyRequestDto

class OrderListAdapter() : RecyclerView.Adapter<OrderListAdapter.OrdersViewHolder>() {

    private var orders: List<MyRequestDto> = ArrayList();

    class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val order_uuid: TextView = itemView.findViewById(R.id.order_uuid);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_order, parent, false)
        val viewHolder = OrdersViewHolder(view)
//        view.setOnClickListener{
//            onSelectListener.invoke(chats[viewHolder.adapterPosition])
//        }
        return viewHolder;
    }

    override fun onBindViewHolder(holder: OrderListAdapter.OrdersViewHolder, position: Int) {
        val currentOrder = orders[position];
        holder.order_uuid.text = currentOrder.requestUuid.toString();
    }

    override fun getItemCount(): Int {
        return orders.size;
    }

    fun setOrders(newOrderList : List<MyRequestDto>){
        orders = newOrderList
        notifyDataSetChanged()
    }
}