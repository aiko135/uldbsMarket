package com.mysoft.uldbsmarket.fragment

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
import com.mysoft.uldbsmarket.adapter.MessageListAdapter
import com.mysoft.uldbsmarket.databinding.ItemCatalogFragmentBinding
import com.mysoft.uldbsmarket.model.Chat
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.vm.CatalogViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class   CatalogFragment : Fragment(R.layout.item_catalog_fragment) {
    private lateinit var binding : ItemCatalogFragmentBinding;
    private lateinit var catalogViewModel: CatalogViewModel;

    private lateinit var goodListAdapter: GoodListAdapter;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.item_catalog_fragment, container, false)
        binding = ItemCatalogFragmentBinding.inflate(inflater)

        catalogViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(CatalogViewModel::class.java)


        //Recycler view
        goodListAdapter = GoodListAdapter(onItemSelect);
        binding.catalogRv.adapter = goodListAdapter;
        binding.catalogRv.layoutManager = LinearLayoutManager(context)

        //Observer
        catalogViewModel.goods.observe(viewLifecycleOwner, Observer {
            goodListAdapter.setGoods(it);
        })

        catalogViewModel.loadChats(onRequestError)
        return binding.root;
    }

    private val onItemSelect : (Good) -> Unit = {
            selected ->
        val bundle : Bundle = Bundle();
        bundle.putString("goodid",selected.uuid)
        findNavController().navigate(R.id.action_nav_catalog_fragment_to_nav_good_fragment, bundle)
    }

    private val onRequestError : () -> Unit = {
        requireActivity().runOnUiThread {
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}