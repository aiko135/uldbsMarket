package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.RegisterFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.vm.RegisterViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import java.util.Date


class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.register_fragment, container, false)
        binding = RegisterFragmentBinding.inflate(inflater)

        registerViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(RegisterViewModel::class.java)

        binding.button4.setOnClickListener { findNavController().navigate(R.id.action_nav_reg_fragment_to_nav_login_fragment) }
        binding.button5.setOnClickListener(onClickRegister)

        //R.layout.register_fragment
        return binding.root;
    }

    //TODO основную валидацию делать в VM
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
            requestAuthorization(newUser);

        }else{
            binding.editTextTextPassword.text.clear();
            binding.editTextTextPassword3.text.clear();
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.password_dont_match, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun requestAuthorization(user :User){
        registerViewModel.register(user,
            {result->
                requireActivity().runOnUiThread {onRegisterResult(result)}
            },
            {
                requireActivity().runOnUiThread {onRequestFault()}
            })
    }
    private fun onRegisterResult(res : RegisterResult){
        switchEnableButtons(true)
        if(res.result && (res.createdAccount != null)){

        }
        else{
            switchEnableButtons(true)

            val toast = Toast.makeText(requireActivity().applicationContext,
                getString(R.string.error)+" "+res.message,
                Toast.LENGTH_SHORT)
            toast.show()
        }
    }
    private fun onRequestFault(){
        switchEnableButtons(true)
        val toast = Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun switchEnableButtons(state : Boolean){
        binding.button4.isEnabled= state;
        binding.button5.isEnabled= state;
    }
}