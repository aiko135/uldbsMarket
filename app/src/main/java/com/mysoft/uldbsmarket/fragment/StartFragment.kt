package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.StartFragmentBinding
import com.squareup.picasso.Picasso

class StartFragment : Fragment(R.layout.start_fragment) {

    private lateinit var binding: StartFragmentBinding;

    override fun onStart() {
        super.onStart()
        uncheckBottomNav();
    }

    private fun uncheckBottomNav(){
        val bottomNavigation =
            activity?.findViewById<BottomNavigationView>(R.id.nav_view) ?: return;
        val menu = bottomNavigation.menu;
        menu.setGroupCheckable(0,true,false);
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.register_fragment, container, false)
        binding = StartFragmentBinding.inflate(inflater)

        testLoading();
        return binding.root;
    }

    private fun testLoading(){

        /*
        Picasso.get()
            .load("http://192.168.0.82:8081/uldbs-back/file/12.jpeg")
            .resize(200, 200)
            .centerCrop()
            .into(binding.imageView3);

         */
        //Загрузка лого магазина
    }
}