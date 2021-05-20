package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class StartFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel;

    override fun onStart() {
        super.onStart()
        uncheckBottomNav();
    }

    private fun uncheckBottomNav(){
//        var bottomNavigation =
//            activity?.findViewById<BottomNavigationView>(R.id.nav_view) ?: return;
//        val menu = bottomNavigation.menu;
//        menu.setGroupCheckable(0,true,false);
//        for (i in 0 until menu.size()) {
//            menu.getItem(i).isChecked = false
//        }
//        menu.setGroupCheckable(0, true, true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.start_fragment, container, false)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java);

        //Запрашиваем запись пользователя из локального хранилища
        userViewModel.userLD.observe(viewLifecycleOwner, Observer(this.onUserDataUpdate))
        userViewModel.readUserInfo();

        return view;
    }

    //Проверка на наличие записи пользователя в локальном хранилище завершена
    private val onUserDataUpdate : ( user : User?) -> Unit = {
        if(it == null)
            findNavController().navigate(R.id.action_nav_start_fragment_to_nav_login_fragment)
        else
            findNavController().navigate(R.id.action_nav_start_fragment_to_nav_profile_fragment)
    }
//    private fun testLoading(){
//        Picasso.get()
//            .load("http://192.168.0.82:8081/uldbs-back/file/12.jpeg")
//            .resize(200, 200)
//            .centerCrop()
//            .into(binding.imageView3);
//        // TODO Загрузка лого магазина
//    }
}