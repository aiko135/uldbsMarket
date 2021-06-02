package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mysoft.uldbsmarket.databinding.ManagerMenuFragmentBinding


class ManagerMenuFragment : Fragment() {
    private val binding by lazy{
        ManagerMenuFragmentBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root;
    }
}