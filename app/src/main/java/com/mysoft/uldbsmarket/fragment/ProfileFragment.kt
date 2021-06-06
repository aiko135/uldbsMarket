package com.mysoft.uldbsmarket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.mysoft.uldbsmarket.MainActivity
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.ProfileFragmentBinding
import com.mysoft.uldbsmarket.databinding.RegisterFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.util.Util
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ProfileFragment : Fragment() {
    private val binding by lazy{
        ProfileFragmentBinding.inflate(layoutInflater);
    }
    private lateinit var userViewModel: UserViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        binding.button2.setOnClickListener(onClickSignOut)

        //observer to viewmodel
        userViewModel.userLD.observe(
            viewLifecycleOwner,
            Observer {
                if(it != null)
                displayUserProfile(it);
            }
        )
        //userViewModel.loadUserData();
        return binding.root
    }

    private val onClickSignOut : View.OnClickListener = View.OnClickListener{
        userViewModel.signOut();
        MainActivity.hideMenuGroups(requireActivity().findViewById<NavigationView>(R.id.nav_view))
        findNavController().popBackStack()
    }


    @SuppressLint("SetTextI18n")
    private fun displayUserProfile(currentUser : User){
        val userRoleCode:Int = currentUser.role.toInt();
        val roleString = when(userRoleCode) {
             2 -> "Manager"
             3 ->"Administrator"
             else -> "User"
        }

       val date_string : String = Util.dateToFormattedString(currentUser.birthDate);

        binding.textView3.text = getString(R.string.profile_info,
            currentUser.name,
            currentUser.email,
            currentUser.phone,
            date_string,
            roleString
        )
    }
}