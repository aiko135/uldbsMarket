package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.adapter.FeedbackListAdapter
import com.mysoft.uldbsmarket.databinding.GoodFragmentBinding
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import com.squareup.picasso.Picasso

class GoodFragment : Fragment() {
    private lateinit var binding: GoodFragmentBinding
    private lateinit var goodViewModel: GoodViewModel;

    private lateinit var feedbackListAdapter: FeedbackListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.good_fragment, container, false)
        binding = GoodFragmentBinding.inflate(inflater)

        goodViewModel  = ViewModelProviders.of(
                requireActivity(),
                ViewModelFactory(requireActivity().applicationContext)
        ).get(GoodViewModel::class.java)

        val goodUuid = arguments?.getString("goodid")
        goodViewModel.goodid = goodUuid;

        //Recycler view
        feedbackListAdapter = FeedbackListAdapter();
        binding.feedbacksRv.adapter = feedbackListAdapter;
        binding.feedbacksRv.layoutManager = LinearLayoutManager(context)

        goodViewModel.goodinfo.observe(viewLifecycleOwner, Observer(onDataUpdate))

        goodViewModel.getFullGoodData(onRequestError);
        return binding.root;
    }

    //Пришло обновление из VM
    private val onDataUpdate : (FullGoodInfo)->Unit = {
        if(it.good != null){
            binding.textView15.text = it.good.name;
            binding.textView17.text = it.good.price.toString() +" "+ getString(R.string.price_units)
            binding.textView18.text = it.good.descr
            if(it.good.imgPath != null){
                val url: String = RetrofitClient.IMAGE_DOWNLOAD_URL + '/' + it.good.imgPath;
                Picasso.get()
                    .load(url)
                   // .resize(200, 200)
                    .error(R.drawable.ic_error_page)
                    //.centerCrop()
                    .into(binding.imageView7);
            }
            feedbackListAdapter.setFeedbacks(it.feedbacks)
        }
    }

    private val onRequestError = {
        requireActivity().runOnUiThread {
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}