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
import com.mysoft.uldbsmarket.databinding.ProfileFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.util.Util
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var userViewModel: UserViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
    private fun displayUserProfile(currentUser : User){
        val userRoleCode:Int = currentUser.role.toInt();
        val roleString = when(userRoleCode) {
             2 -> "Manager"
             3 ->"Administrator"
             else -> "User"
        }

       val date_string : String = Util.dateToFormattedString(currentUser.birthDate);

        //TODO REFACTOR WITH DATABINDING
        binding.textView3.text =
            "Name: ${currentUser.name} \n" + "Login: ${currentUser.email}\n" + "Phone: ${currentUser.phone}\n" + "Birth date: "+date_string +"\n\n" +
                            "Account: "+roleString;
    }
}