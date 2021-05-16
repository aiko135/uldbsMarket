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
import com.mysoft.uldbsmarket.databinding.RegisterFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import java.util.Date


class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.register_fragment, container, false)
        binding = RegisterFragmentBinding.inflate(inflater)

        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        userViewModel.registerResultLD.observe(viewLifecycleOwner, Observer(onRegisterResult))
        userViewModel.userLD.observe(viewLifecycleOwner, Observer(onUserFound))

        binding.button4.setOnClickListener { findNavController().navigate(R.id.action_nav_reg_fragment_to_nav_login_fragment) }
        binding.button5.setOnClickListener(onClickRegister)

        //R.layout.register_fragment
        return binding.root;
    }

    //TODO основную валидацию делать в Model
    private val onClickRegister : View.OnClickListener = View.OnClickListener{
        if(binding.editTextTextPassword.text.toString() == binding.editTextTextPassword3.text.toString()){
            switchEnableButtons(false);
            var newUser : User = User(
                "",
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextTextPassword.text.toString(),
                1,
                binding.editTextTextPersonName2.text.toString(),
                Date(2000,12,12),
                binding.editTextPhone.text.toString()
            )
            userViewModel.register(newUser); // register

        }else{
            binding.editTextTextPassword.text.clear();
            binding.editTextTextPassword3.text.clear();
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.password_dont_match, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private val onRegisterResult : (res : RegisterResult) ->Unit = {
        if (!it.result || it.createdAccount == null) {
            switchEnableButtons(true)

            val toast = Toast.makeText(requireActivity().applicationContext,
                getString(R.string.error)+" "+it.message,
                Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private val onUserFound : (data : User? ) -> Unit = {
        //После регистрации
        switchEnableButtons(true)
        if(it != null)
            requireView().findNavController().navigate(R.id.nav_profile_fragment, null);
    }

    private fun switchEnableButtons(state : Boolean){
        binding.button4.isEnabled= state;
        binding.button5.isEnabled= state;
    }
}