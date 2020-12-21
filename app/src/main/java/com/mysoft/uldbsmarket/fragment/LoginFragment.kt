package com.mysoft.uldbsmarket.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.LoginFragmentBinding
import com.mysoft.uldbsmarket.model.dto.LoginResult
import com.mysoft.uldbsmarket.vm.LoginViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import kotlinx.android.synthetic.main.login_fragment.*
import kotlin.math.log

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel;
    //Сгенерированный класс для датабайндинга
    private lateinit var binding: LoginFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.login_fragment, container, false)
        binding = LoginFragmentBinding.inflate(inflater)
        loginViewModel = ViewModelProviders.of(requireActivity(), ViewModelFactory()).get(LoginViewModel::class.java)

        //view.findViewById<Button>(R.id.button3).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_reg_fragment,null));

        binding.button3.setOnClickListener{Navigation.createNavigateOnClickListener(R.id.nav_reg_fragment,null)}
        binding.button.setOnClickListener{loginViewModel.doLogin(
            binding.editTextTextPersonName.text.toString(),
            binding.editTextTextPassword2.text.toString(),
            onRequestError
        )}

        loginViewModel.loginResultLiveData.observe(viewLifecycleOwner, Observer { onRequestResultObserver(it) })

        return binding.root;
    }

    private val onRequestResultObserver : ((data : LoginResult)->Unit) = {
        if(it.result){
            //Успешная авторизация
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.success, Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            //Пришел результат о неуспешной авторизации
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.wrong_login_data, Toast.LENGTH_SHORT)
            toast.show()
            binding.editTextTextPassword2.text.clear();
        }
    }

    private val onRequestError = {
        requireActivity().runOnUiThread {
            val toast = Toast.makeText(requireActivity().applicationContext, R.string.request_err, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    val onClickLogin : View.OnClickListener = object : View.OnClickListener{
            override fun onClick(v: View?) {
                view?.findNavController()?.navigate(R.id.nav_reg_fragment);
            }
    }

}