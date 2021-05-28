package com.mysoft.uldbsmarket.fragment

import android.graphics.Paint
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
import com.mysoft.uldbsmarket.adapter.GoodListAdapter
import com.mysoft.uldbsmarket.databinding.ChatFragmentBinding
import com.mysoft.uldbsmarket.databinding.ItemCatalogFragmentBinding
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.model.ReqResult
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory


class   CatalogFragment : Fragment() {
    private val binding by lazy{
        ItemCatalogFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var goodViewModel: GoodViewModel;

    private lateinit var goodListAdapter: GoodListAdapter;

    private val onItemSelect : (Good) -> Unit = {
        val bundle : Bundle = Bundle();
        bundle.putString("goodid",it.uuid)
        findNavController().navigate(R.id.action_nav_catalog_fragment_to_nav_good_fragment, bundle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        goodViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(GoodViewModel::class.java)

        binding.grade.paintFlags = binding.grade.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.button8.paintFlags = binding.button8.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        //Recycler view
        goodListAdapter = GoodListAdapter(onItemSelect);
        binding.catalogRv.adapter = goodListAdapter;
        binding.catalogRv.layoutManager = LinearLayoutManager(context)

        //Observer
        goodViewModel.goodsLD.observe(viewLifecycleOwner, Observer {
            if(it.isSuccess){
                goodListAdapter.setGoods(it.entity!!)
            }
            else{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })

        goodViewModel.loadGoods();
        return binding.root;
    }

//    override fun onCreateOptionsMenu(menu: Menu){
//        activity?.menuInflater?.inflate(R.menu.toolbar_search_menu, menu);
//    }
}