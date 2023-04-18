package com.example.mvvm.auth

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController()
        val preferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (preferences != null) {

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                if (preferences.getBoolean("isLoggedIn", false)) {
                    findNavController().navigate(R.id.action_splash_to_home2)
                } else {
                    findNavController().navigate(R.id.action_splash_to_login)
                }
            }, 3000) // delay for 3 seconds

        }

    }
}