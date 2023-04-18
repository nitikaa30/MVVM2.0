package com.example.mvvm.auth.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.adapter.MyAdapter
import com.example.mvvm.databinding.FragmentHomeBinding
import com.example.mvvm.model.details.DetailsData
import com.example.mvvm.model.details.Details
import com.example.mvvm.model.login.LoginResponse
import com.example.mvvm.retrofit.Retrofit
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        binding?.recycle?.layoutManager= LinearLayoutManager(context)
        val token=getToken()
        Log.d("fetche1", getToken().toString())
        Retrofit.apiInterface.getInfo("Bearer $token").enqueue(object : Callback<Details> {
            override fun onResponse(call: Call<Details>, response: Response<Details>) {
//                Toast.makeText(context,getToken(), Toast.LENGTH_LONG).show()
                Log.d("yo","$token")
                val list: ArrayList<DetailsData>?=response.body()?.data
                val adapter= MyAdapter(list)
                binding?.recycle?.adapter=adapter
            }
            override fun onFailure(call: Call<Details>, t: Throwable) {
                Toast.makeText(context,"nooo", Toast.LENGTH_LONG).show()
            }
        })
        return binding?.root
    }
    private fun getToken() : String? {
        val sharedPreferences = activity?.getSharedPreferences("Token", Context.MODE_PRIVATE)

        val userString = sharedPreferences?.getString("Token", null)
        val userObj = Gson().fromJson(userString, LoginResponse.Data::class.java)
        return userObj.Token
    }
}