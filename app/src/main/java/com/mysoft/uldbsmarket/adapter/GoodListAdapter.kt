package com.mysoft.uldbsmarket.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.squareup.picasso.Picasso

class GoodListAdapter(private val onSelectListener : (c: Good)->Unit): RecyclerView.Adapter<GoodListAdapter.GoodViewHolder>() {
    private var goods: List<Good> = ArrayList();

    class GoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textView6);
        val price: TextView = itemView.findViewById(R.id.textView14);
        val itemImg: ImageView = itemView.findViewById(R.id.imageView4)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodListAdapter.GoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_catalog_good, parent, false)
        val viewHolder = GoodListAdapter.GoodViewHolder(view)
        view.setOnClickListener{
            onSelectListener.invoke(goods[viewHolder.adapterPosition])
        }
        return viewHolder;
    }

    override fun getItemCount(): Int {
       return goods.size;
    }

    override fun onBindViewHolder(holder: GoodViewHolder, position: Int) {
        val current : Good = goods[position];

        holder.title.text = current.name
        holder.price.text = "Price :${current.price.toString()} RUB"

        //if(current.imgPath != null) {
            val url: String = "${RetrofitClient.IMAGE_DOWNLOAD_URL}/${current.imgPath}";
            Picasso.get()
                .load(url)
                .resize(200, 200)
                .error(R.drawable.ic_error_page)
                .centerCrop()
                .into(holder.itemImg);
        //}else{
            //TODO сдесь дефолтную заставку если картинка не предусмотрена
            //holder.itemImg.setImageDrawable(Drawable(R.drawable.ic_error_page));
        //}
    }

    fun setGoods(newGoods: List<Good>) {
        goods = newGoods
        notifyDataSetChanged()
    }
}