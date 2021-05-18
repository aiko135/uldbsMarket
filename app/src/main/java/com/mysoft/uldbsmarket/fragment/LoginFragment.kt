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
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.LoginFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel;
    //Сгенерированный класс для датабайндинга
    private lateinit var binding: LoginFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.login_fragment, container, false)
        binding = LoginFragmentBinding.inflate(inflater)
        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        binding.button3.setOnClickListener{findNavController().navigate(R.id.action_nav_login_fragment_to_nav_reg_fragment)}

        userViewModel.loginResultLD.observe(viewLifecycleOwner, Observer(onRequestResultObserver) )
        userViewModel.userLD.observe(viewLifecycleOwner, Observer(onUserFound))

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


    private val onRequestResultObserver : (data : LoginResult)->Unit = {
        switchEnableButtons(true);
        if(!it.result){
            //Пришел результат о неуспешной авторизации
            val toast = Toast.makeText(
                requireActivity().applicationContext,
                getString(R.string.error)+" " +it.error,
                Toast.LENGTH_SHORT
            )
            toast.show()
            binding.editTextTextPassword2.text.clear();
        }
    }

    private val onUserFound: (User?)->Unit = {
        if(it != null){
            binding.editTextTextPassword2.text.clear();
            requireView().findNavController().navigate(R.id.nav_profile_fragment, null);
        }
    }

    private fun switchEnableButtons(state : Boolean){
        binding.button.isEnabled= state;
        binding.button3.isEnabled= state;
    }
}