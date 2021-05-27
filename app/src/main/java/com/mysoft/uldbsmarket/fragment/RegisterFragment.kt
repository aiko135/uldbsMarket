package com.mysoft.uldbsmarket.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mysoft.uldbsmarket.R
import com.mysoft.uldbsmarket.databinding.RegisterFragmentBinding
import com.mysoft.uldbsmarket.fragment.dialog.DatePickerFragment
import com.mysoft.uldbsmarket.model.User
import com.mysoft.uldbsmarket.model.dto.RegisterResult
import com.mysoft.uldbsmarket.util.Util
import com.mysoft.uldbsmarket.vm.UserViewModel
import com.mysoft.uldbsmarket.vm.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var userViewModel: UserViewModel

    //Дата отражаемая на странице
    private val selectedBirthDay by lazy{
        MutableLiveData<Date>(Date())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegisterFragmentBinding.inflate(inflater)

        userViewModel = ViewModelProviders.of(
            requireActivity(),
            ViewModelFactory(requireActivity().applicationContext)
        ).get(UserViewModel::class.java)

        userViewModel.registerResultLD.observe(viewLifecycleOwner, Observer{
            if (!it.result || it.createdAccount == null) {
                switchEnableButtons(true)
                val toast = Toast.makeText(requireActivity().applicationContext,
                    getString(R.string.error)+" "+it.message,
                    Toast.LENGTH_SHORT)
                toast.show()
            }
        })
        userViewModel.userLD.observe(viewLifecycleOwner, Observer{
            //После регистрации
            switchEnableButtons(true)
            if(it != null)
                requireView().findNavController().navigate(R.id.nav_profile_fragment, null);
        })

        binding.button4.setOnClickListener { findNavController().navigate(R.id.action_nav_reg_fragment_to_nav_login_fragment) }
        binding.button5.setOnClickListener(onClickRegister)
        binding.buttonOpenPicker.setOnClickListener(onClickOpenDatePicker);

        selectedBirthDay.observe(viewLifecycleOwner, Observer(onDateUpdated))
        selectedBirthDay.postValue(Date());

        return binding.root;
    }

    //TODO основную валидацию делать в Model
    private val onClickRegister : View.OnClickListener = View.OnClickListener{
        if(binding.editTextTextPassword.text.toString() == binding.editTextTextPassword3.text.toString()){
            switchEnableButtons(false);
            var newUser : User = User(
                UUID.randomUUID().toString(),
                binding.editTextTextEmailAddress.text.toString(),
                binding.editTextTextPassword.text.toString(),
                1,
                binding.editTextTextPersonName2.text.toString(),
                selectedBirthDay.value!!,
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

    private fun switchEnableButtons(state : Boolean){
        binding.button4.isEnabled= state;
        binding.button5.isEnabled= state;
    }

    //Обновление отражаемой на странице даты рождения
    private val onDateUpdated : (date : Date)->Unit = {
        lifecycleScope.launch(Dispatchers.Main) {
            val defaultDate: String = Util.dateToFormattedString(it);
            binding.editTextBirthDate.text = defaultDate;
        }
    }

    //Открытие date picker
    private val onClickOpenDatePicker: View.OnClickListener = View.OnClickListener{
        val fragmentDialog = DatePickerFragment(
            requireActivity(),
            object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    //Нажатие на кнопку установки текущего времени
                    selectedBirthDay.postValue(Date(year-1900, month, dayOfMonth));
                }
            }
        );
        fragmentDialog.show(requireActivity().supportFragmentManager,"datePick")
    }

}