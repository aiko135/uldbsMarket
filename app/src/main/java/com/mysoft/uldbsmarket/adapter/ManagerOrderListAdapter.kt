package com.mysoft.uldbsmarket.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.dto.MyRequestDto
import com.mysoft.uldbsmarket.util.Util
import java.util.*
import kotlin.collections.ArrayList

class ManagerOrderListAdapter (
    private val context: Context,
    private val onButtonClickListener: (Button, UUID)->Unit
) : RecyclerView.Adapter<ManagerOrderListAdapter.OrdersViewHolder>() {

    private var orders: List<MyRequestDto> = ArrayList();

    class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val order_uuid: TextView = itemView.findViewById(R.id.order_id);
        val goods_conteiner: ViewGroup = itemView.findViewById(R.id.goods_contaier)
        val user_data: TextView = itemView.findViewById(R.id.manager_data)
        val orders_status: TextView = itemView.findViewById(R.id.order_status_tv)

        val button_go_chat: Button = itemView.findViewById(R.id.go_to_chat)
        val button_submit: Button = itemView.findViewById(R.id.submitButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_order_manager, parent, false)
        val viewHolder = OrdersViewHolder(view)
//        view.setOnClickListener{
//            onSelectListener.invoke(chats[viewHolder.adapterPosition])
//        }
        return viewHolder;
    }

    override fun onBindViewHolder(holder: ManagerOrderListAdapter.OrdersViewHolder, position: Int) {
        val currentOrder = orders[position];
        holder.order_uuid.text = currentOrder.requestUuid.toString();
        holder.user_data.text = context.getString(R.string.info_about_manager,
            currentOrder.contactorName,
            currentOrder.contactorEmail,
            currentOrder.contactorPhone
        );

        holder.orders_status.text =  if(currentOrder.statusHistoryList.isEmpty())
            "no status mark"
        else
            with(Util.getCurrentStatusFromHistory(currentOrder.statusHistoryList)){
                "${status.name}\n${Util.dateToFormattedString(setupTimestamp)}"}

        holder.goods_conteiner.removeAllViews()
        for(good in currentOrder.goodRequestList){
            val good_tv = generateGoodView()
            good_tv.text = "${good.good.name} - - - - ${Util.priceToString(good.good.price)}" ;
            holder.goods_conteiner.addView(good_tv)
        }

        holder.button_go_chat.setOnClickListener{
            onButtonClickListener.invoke(holder.button_go_chat, currentOrder.contactorUuid)
        };
    }

    override fun getItemCount(): Int {
        return orders.size;
    }

    fun setOrders(newOrderList : List<MyRequestDto>){
        orders = newOrderList
        notifyDataSetChanged()
    }

    private fun generateGoodView(): TextView {
        val lparams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val tv : TextView = TextView(context);
        tv.layoutParams = lparams;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
        return tv;
    }
}