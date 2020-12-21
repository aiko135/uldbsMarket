package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mysoft.uldbsmarket.R

class StartFragment : Fragment(R.layout.start_fragment) {
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
}