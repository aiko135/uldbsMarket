package com.mysoft.uldbsmarket.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.ProfileFragmentBinding
import com.mysoft.uldbsmarket.databinding.RegisterFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.vm.LoginViewModel
import com.mysoft.uldbsmarket.vm.ProfileViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var profileViewModel: ProfileViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.profile_fragment, container, false)
        binding =  ProfileFragmentBinding.inflate(inflater)

        profileViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(ProfileViewModel::class.java)

        binding.button2.setOnClickListener(onClickSignOut)

        //observer to viewmodel
        profileViewModel.userData.observe(
            viewLifecycleOwner,
            Observer {
                displayUserProfile(it);
            }
        )

        profileViewModel.loadUserData();
        return binding.root
    }

    private val onClickSignOut : View.OnClickListener = View.OnClickListener{
        profileViewModel.signOut();
        findNavController().popBackStack()
    }


    @SuppressLint("SetTextI18n")
    private fun displayUserProfile(u : User){
        var role : String = "User"
        when{
            u.role.toInt() == 2 ->{
                role = "Manager";
            }
            u.role.toInt() == 3  ->{
                role = "Administrator"
            }
        }

        binding.textView3.text =
            "Name: ${u.name} \n" + "Login: ${u.email}\n" + "Phone: ${u.phone}\n" + "Birth date: ${u.birthDate.date.toString()}\n" +
                            "Account: "+role;
    }
}