package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.CartFragmentBinding
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mysoft.uldbsmarket.adapter.GoodListAdapter
import com.mysoft.uldbsmarket.databinding.ItemCatalogFragmentBinding
import com.mysoft.uldbsmarket.util.Util
import com.mysoft.uldbsmarket.vm.UserViewModel

class CartFragment: Fragment() {
    private val binding by lazy{
        CartFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var goodViewModel: GoodViewModel;
    private lateinit var userViewModel: UserViewModel;

    private lateinit var goodListAdapter: GoodListAdapter;

    private val onCartGoodsFound : (List<Good>) -> Unit = {
        if(it.isNotEmpty()){
            binding.buttonPay.isEnabled = true;
            binding.emptyCarTw.visibility = View.GONE;
            binding.buttonClean.visibility = View.VISIBLE;
        }
        else{
            binding.buttonPay.isEnabled = false;
            binding.emptyCarTw.visibility = View.VISIBLE;
            binding.buttonClean.visibility = View.GONE;
        }
        goodListAdapter.setGoods(it);

        val price = goodViewModel.getSummPrice();
        binding.priceTW.text = Util.priceToString(price)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        goodViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(GoodViewModel::class.java)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        //Обработчики кнопок
        binding.buttonClean.setOnClickListener{
            goodViewModel.cleanCart();
        }
        binding.buttonPay.setOnClickListener{
            //Если пользователь авторизован - переход на страницу с оплатой. Иначе - редирект на страницу авторизации
            if(userViewModel.userLD.value != null)
                findNavController().navigate(R.id.action_nav_cart_fragment_to_nav_payment_fragment)
            else
                findNavController().navigate(R.id.action_nav_cart_fragment_to_nav_login_fragment)
        }

        //Recycler view
        goodListAdapter = GoodListAdapter(this){};
        binding.goodsRv.adapter = goodListAdapter;
        binding.goodsRv.layoutManager = LinearLayoutManager(context)

        //Обсервер
        goodViewModel.cartLD.observe(viewLifecycleOwner, Observer(onCartGoodsFound))

        //Начинаем загрузку данных для фрагмента
        goodViewModel.loadGoodsCart();
        return binding.root;
    }

}