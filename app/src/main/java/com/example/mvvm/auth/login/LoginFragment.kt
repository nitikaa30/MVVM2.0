package com.example.mvvm.auth.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentLoginBinding
import com.example.mvvm.model.login.LoginModel
import com.example.mvvm.repository.ApiResponse
import com.google.gson.Gson


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+\$"
    private val passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*_=+-]).{8,}\$"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.nextBtn1.setOnClickListener {
            validations()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data.let {
                        val preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        preferences.edit().putBoolean("isLoggedIn", true).apply()
                        it.data?.Token
                        findNavController().navigate(R.id.action_login_to_home2)

                        val gson = Gson()
                        val jsonString = gson.toJson(it.data).toString()
                        val sharedPreferences1 = activity?.getSharedPreferences("Token", Context.MODE_PRIVATE)
                        val editor1 = sharedPreferences1?.edit()
                        editor1.apply {
                            editor1?.putString("Token", jsonString) }?.apply()
                    }
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context,"yoyoyooy",Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun validations() {
        val email = binding.user1.text.toString().trim()
        val password = binding.pass1.text.toString().trim()

        if (email.isEmpty()) {
            binding.user1.error = "Email required"
            return
        }

        if (password.isEmpty()) {
            binding.pass1.error = "Password Required"
            return
        }

        if (!email.matches(emailPattern.toRegex())) {
            binding.user1.error = "Invalid Email"
            return
        }

        if (!password.matches(passPattern.toRegex())) {
            binding.pass1.error = "Invalid Password"
            return
        }

        val loginBody = LoginModel(email, password)
        loginViewModel.login(loginBody)
    }
}



//class LoginFragment : Fragment() {
//    private lateinit var loginViewModel: LoginViewModel
//    private val getUsersViewModel: GetUsersViewModel by viewModels()
//    private var emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+\$"
//    private var passPattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*_=+-]).{8,}\$"
//    private lateinit var binding: FragmentLoginBinding
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding= FragmentLoginBinding.inflate(inflater,container,false)
//        binding.nextBtn1.setOnClickListener {
//            validations()
//        }
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        loginViewModel= ViewModelProvider(this)[LoginViewModel::class.java]
//        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { loginResponse ->
//            if (loginResponse != null) {
//                loginResponse.data?.let {
//                    val preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//                    preferences.edit().putBoolean("isLoggedIn", true).apply()
//                    loginResponse.data.Token
//                    findNavController().navigate(R.id.action_login_to_home2)
////                    val sharedPreferences1 = activity?.getSharedPreferences("Token", Context.MODE_PRIVATE)
////                    val editor1 = sharedPreferences1?.edit()
////                    editor1.apply {
////                        editor1?.putString("Token",
////                            loginResponse.data.Token
////                        ) }?.apply()
//
//                    val gson = Gson()
//                    val jsonString = gson.toJson(loginResponse.data).toString()
//                    val sharedPreferences1 = activity?.getSharedPreferences("Token", Context.MODE_PRIVATE)
//                    val editor1 = sharedPreferences1?.edit()
//                    editor1.apply {
//                        editor1?.putString("Token", jsonString) }?.apply()
//                }
//            } else {
//                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//    private fun validations(){
//        if (binding.user1.text.toString().isEmpty()) {
//            binding.user1.error = "Email required"
//        }
//        if (binding.pass1.text.toString().isEmpty()) {
//            binding.pass1.error = "Password Required"
//        } else {
//            if (binding.user1.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())
//                && binding.pass1.text.toString().trim { it <= ' ' }
//                    .matches(passPattern.toRegex())
//            ) {
//                val email = binding.user1.text.toString()
//                val password = binding.pass1.text.toString()
//                val loginBody = LoginModel(email, password)
//                loginViewModel.login(loginBody)
//            }
//        }
//
//    }
//
//
//}
