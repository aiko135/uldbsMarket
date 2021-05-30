package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.braintreepayments.cardform.view.CardForm
import com.bumptech.glide.Glide
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.PaymentFragmentBinding
import com.mysoft.uldbsmarket.databinding.ProfileFragmentBinding
import com.mysoft.uldbsmarket.model.dto.FullGoodInfo
import com.mysoft.uldbsmarket.network.RetrofitClient
import com.mysoft.uldbsmarket.vm.GoodViewModel
import com.mysoft.uldbsmarket.vm.RequestViewModel
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class PaymentFragment : Fragment(){
    private val binding by lazy{
        PaymentFragmentBinding.inflate(layoutInflater);
    }

    private lateinit var userViewModel: UserViewModel;
    private lateinit var requestViewModel: RequestViewModel;
    private lateinit var goodViewModel: GoodViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        goodViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(GoodViewModel::class.java)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)
        requestViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(RequestViewModel::class.java)


        val cardForm : CardForm = binding.cardForm;
        cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .cardholderName(CardForm.FIELD_REQUIRED)
            .postalCodeRequired(true)
            .mobileNumberRequired(true)
            .mobileNumberExplanation("SMS is required on this number")
            .actionLabel("Purchase")
            .setup(activity as AppCompatActivity?);

        binding.buttonSubmit.setOnClickListener{
            if(userViewModel.userLD.value == null){
                Toast.makeText(requireContext(), getString(R.string.not_authorized), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if(goodViewModel.cartLD.value == null || goodViewModel.cartLD.value!!.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.empty_cart), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            else{
                binding.buttonSubmit.isEnabled = false;
                requestViewModel.postNewOrder(
                    userViewModel.userLD.value!!.uuid,
                    cardForm.cardNumber.toString(),
                    goodViewModel.cartLD.value!!
                )
            }
        }

        requestViewModel.isNewOrderAddedSLD.observe(viewLifecycleOwner, Observer{
            if(it.isSuccess && it.entity != null){
                Toast.makeText(requireActivity().applicationContext, getString(R.string.success), Toast.LENGTH_SHORT).show()
                //TODO empty cart
                //TODO redirect to my orders page
            }
            else{
                Toast.makeText(requireActivity().applicationContext, it.message, Toast.LENGTH_SHORT).show()
                binding.buttonSubmit.isEnabled = true;
            }
        })

        binding.buttonSubmit.isEnabled = true;
        return binding.root;
    }
}