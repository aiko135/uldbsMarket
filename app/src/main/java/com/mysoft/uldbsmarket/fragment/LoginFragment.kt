package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.LoginFragmentBinding
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.vm.LoginViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import kotlinx.android.synthetic.main.login_fragment.*
import kotlin.math.log

//TODO Зарефакторить так как в RegisterFragment так как делал этот модуль первым
class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel;
    //Сгенерированный класс для датабайндинга
    private lateinit var binding: LoginFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.login_fragment, container, false)
        binding = LoginFragmentBinding.inflate(inflater)
        loginViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(LoginViewModel::class.java)

        binding.button3.setOnClickListener{findNavController().navigate(R.id.action_nav_login_fragment_to_nav_reg_fragment)}
        binding.button.setOnClickListener{
            loginViewModel.doLogin(
                binding.editTextTextPersonName.text.toString(),
                binding.editTextTextPassword2.text.toString(),
                onRequestError
            );
            switchEnableButtons(false);
        }

        loginViewModel.loginResultLiveData.observe(viewLifecycleOwner, Observer { onRequestResultObserver(it) })

        requestUserPrefs();  //TODO REFACTOR;
        return binding.root;
    }

    private val onRequestResultObserver : ((data : LoginResult)->Unit) = {
            switchEnableButtons(true);
            if (it.result) {
                //Успешная авторизация
                if (it.user != null) {
                    loginViewModel.writeUserInfo(it.user) {
                        requireActivity().runOnUiThread {
                           //requireView().findNavController().navigate(R.id.action_nav_login_fragment_to_nav_profile_fragment)
                            requireView().findNavController().navigate(R.id.nav_profile_fragment, null);
                        }
                    }
                } else {
                    requireActivity().runOnUiThread {
                        val toast = Toast.makeText(
                            requireActivity().applicationContext,
                            R.string.missing_data,
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }

            }
            else {
                //Пришел результат о неуспешной авторизации
                val toast = Toast.makeText(
                    requireActivity().applicationContext,
                    R.string.wrong_login_data,
                    Toast.LENGTH_SHORT
                )
                toast.show()
                binding.editTextTextPassword2.text.clear();
            }
    }


    private val onRequestError = {
        requireActivity().runOnUiThread {
            switchEnableButtons(true);
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun requestUserPrefs(){
        loginViewModel.readUserInfo { u : User? ->
            if(u != null){
                requireActivity().runOnUiThread {
                    findNavController().navigate(R.id.action_nav_login_fragment_to_nav_profile_fragment)
                }
            }
        }
    }

    private fun switchEnableButtons(state : Boolean){
        binding.button.isEnabled= state;
        binding.button3.isEnabled= state;
    }
}