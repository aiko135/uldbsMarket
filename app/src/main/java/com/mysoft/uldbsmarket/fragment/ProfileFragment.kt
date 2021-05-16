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
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.ProfileFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var userViewModel: UserViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.profile_fragment, container, false)
        binding =  ProfileFragmentBinding.inflate(inflater)

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
        //TODO REFACTOR DATABINDING
        binding.textView3.text =
            "Name: ${u.name} \n" + "Login: ${u.email}\n" + "Phone: ${u.phone}\n" + "Birth date: ${u.birthDate.date.toString()}\n" +
                            "Account: "+role;
    }
}