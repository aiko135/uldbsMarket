package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.BasketFragmentBinding
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mysoft.uldbsmarket.adapter.GoodListAdapter
import com.mysoft.uldbsmarket.util.Util

class CartFragment: Fragment() {
    private lateinit var binding: BasketFragmentBinding;
    private lateinit var goodViewModel: GoodViewModel;

    private lateinit var goodListAdapter: GoodListAdapter;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BasketFragmentBinding.inflate(inflater)

        goodViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(GoodViewModel::class.java)

        binding.buttonClean.setOnClickListener{
            goodViewModel.cleanCart();
        }

        //Recycler view
        goodListAdapter = GoodListAdapter{};
        binding.goodsRv.adapter = goodListAdapter;
        binding.goodsRv.layoutManager = LinearLayoutManager(context)

        goodViewModel.cartLD.observe(viewLifecycleOwner, Observer(onCartGoodsFound))
        goodViewModel.loadGoodsCart();

        return binding.root;
    }

    private val onCartGoodsFound : (List<Good>) -> Unit = {
       if(it.isNotEmpty()){
            binding.emptyCarTw.visibility = View.GONE;
            binding.buttonClean.visibility = View.VISIBLE;
       }
        else{
           binding.emptyCarTw.visibility = View.VISIBLE;
           binding.buttonClean.visibility = View.GONE;
       }
        goodListAdapter.setGoods(it);

        val price = goodViewModel.getSummPrice();
        binding.priceTW.text = Util.priceToString(price)
    }
}