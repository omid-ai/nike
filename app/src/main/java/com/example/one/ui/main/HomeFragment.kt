package com.example.one.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.one.common.base.NikeFragment
import com.example.one.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment: NikeFragment() {

    lateinit var binding: FragmentHomeBinding
    val homeViewModel:HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner){
            Timber.i("this is the list of banners-> $it")
        }
    }
}