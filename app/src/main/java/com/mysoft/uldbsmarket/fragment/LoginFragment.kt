package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.mysoft.uldbsmarket.MainActivity
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.LoginFragmentBinding
import com.mysoft.uldbsmarket.model.dto.RequestError
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel;
    //Сгенерированный класс для датабайндинга
    private val binding by lazy{
        LoginFragmentBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        binding.button3.setOnClickListener{findNavController().navigate(R.id.action_nav_login_fragment_to_nav_reg_fragment)}

        userViewModel.loginResultLD.observe(viewLifecycleOwner, Observer {
            switchEnableButtons(true);
            if(it is RequestError){
                //Пришел результат о неуспешной авторизации
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error)+" " +it.message,
                    Toast.LENGTH_SHORT
                ).show()
                binding.editTextTextPassword2.text.clear();
            }
        })
        userViewModel.userLD.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.editTextTextPassword2.text.clear();
                MainActivity.showMenuGroups(it,requireActivity().findViewById<NavigationView>(R.id.nav_view))
                requireView().findNavController().navigate(R.id.nav_profile_fragment, null);
            }
        })

        binding.button.setOnClickListener{
            //Нажатие на кнопку логина
            userViewModel.doLogin(
                binding.editTextTextPersonName.text.toString(),
                binding.editTextTextPassword2.text.toString(),
            );
            switchEnableButtons(false);
        }
        return binding.root;
    }


    private fun switchEnableButtons(state : Boolean){
        binding.button.isEnabled= state;
        binding.button3.isEnabled= state;
    }
}