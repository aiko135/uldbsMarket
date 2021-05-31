package com.mysoft.uldbsmarket.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.dto.MyRequestDto
import com.mysoft.uldbsmarket.util.Util.Companion.getCurrentStatusFromHistory
import com.mysoft.uldbsmarket.util.Util.Companion.priceToString

class OrderListAdapter(private val context: Context) : RecyclerView.Adapter<OrderListAdapter.OrdersViewHolder>() {

    private var orders: List<MyRequestDto> = ArrayList();

    class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val order_uuid: TextView = itemView.findViewById(R.id.order_id);
        val goods_conteiner: ViewGroup = itemView.findViewById(R.id.goods_contaier)
        val manager_data: TextView = itemView.findViewById(R.id.manager_data)
        val orders_status: TextView = itemView.findViewById(R.id.order_status_tv)
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
        holder.manager_data.text =
            "Manager name: ${currentOrder.managerName} \n"+
            "Manager email: ${currentOrder.managerEmail}\n"+
                    "Manager phone: ${currentOrder.managerPhone}\n";

        holder.orders_status.text =  if(currentOrder.statusHistoryList.isEmpty())
            "no status mark"
        else
            getCurrentStatusFromHistory(currentOrder.statusHistoryList).status.name;

        for(good in currentOrder.goodRequestList){
            val good_tv = generateGoodView()
            good_tv.text = "${good.good.name} - - - - ${priceToString(good.good.price)}" ;
            holder.goods_conteiner.addView(good_tv)
        }
    }

    override fun getItemCount(): Int {
        return orders.size;
    }

    fun setOrders(newOrderList : List<MyRequestDto>){
        orders = newOrderList
        notifyDataSetChanged()
    }

    private fun generateGoodView():TextView{
        val lparams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val tv : TextView = TextView(context);
        tv.layoutParams = lparams;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
        return tv;
    }
}