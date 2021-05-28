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
import com.mysoft.uldbsmarket.databinding.LoginFragmentBinding
import com.mysoft.uldbsmarket.model.ReqResult
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import com.squareup.picasso.Picasso

class GoodFragment : Fragment() {
    private val binding by lazy{
        GoodFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var goodViewModel: GoodViewModel;

    private lateinit var feedbackListAdapter: FeedbackListAdapter

    //Пришло обновление из VM
    private val onDataUpdate : (FullGoodInfo : ReqResult<FullGoodInfo>)->Unit = {
        if(it.isSuccess && it.entity != null){
            val data : FullGoodInfo = it.entity;
            binding.textView15.text = data.good.name;
            binding.textView17.text = data.good.price.toString() +" "+ getString(R.string.price_units)
            binding.textView18.text = data.good.descr
            if(data.good.imgPath != null){
                val url: String = "${RetrofitClient.IMAGE_DOWNLOAD_URL}/${data.good.imgPath}";
                Picasso.get()
                    .load(url)
                    // .resize(200, 200)
                    .error(R.drawable.ic_error_page)
                    //.centerCrop()
                    .into(binding.imageView7);
            }
            feedbackListAdapter.setFeedbacks(data.feedbacks)
        }
        else{
            val toast = Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private val onClickAddToCart : View.OnClickListener = View.OnClickListener{
        goodViewModel.addSelectedGoodIntoCart();
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        goodViewModel  = ViewModelProviders.of(
                requireActivity(),
                ViewModelFactory(requireActivity().applicationContext)
        ).get(GoodViewModel::class.java)

        val goodUuid = arguments?.getString("goodid")
        goodViewModel.goodid = goodUuid;

        binding.button9.setOnClickListener(onClickAddToCart)

        //Recycler view
        feedbackListAdapter = FeedbackListAdapter();
        binding.feedbacksRv.adapter = feedbackListAdapter;
        binding.feedbacksRv.layoutManager = LinearLayoutManager(context)

        goodViewModel.selectedGoodLD.observe(viewLifecycleOwner, Observer(onDataUpdate))
        goodViewModel.isAddedToCardSLD.observe(viewLifecycleOwner, Observer{
            if(it){
                binding.button9.isEnabled = false;
                Toast.makeText(requireContext(), getString(R.string.done), Toast.LENGTH_SHORT).show()
            }
        })

        goodViewModel.getFullGoodData();
        return binding.root;
    }
}