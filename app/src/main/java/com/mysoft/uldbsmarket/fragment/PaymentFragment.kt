package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.braintreepayments.cardform.view.CardForm
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.PaymentFragmentBinding
import com.mysoft.uldbsmarket.databinding.ProfileFragmentBinding

class PaymentFragment : Fragment(R.layout.map_fragment){
    private lateinit var binding: PaymentFragmentBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PaymentFragmentBinding.inflate(inflater)

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

        return binding.root;
    }
}