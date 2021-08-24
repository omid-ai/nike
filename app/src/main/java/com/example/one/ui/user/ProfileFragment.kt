package com.example.one.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.one.R
import com.example.one.common.base.NikeFragment
import com.example.one.databinding.FragmentProfileBinding
import com.example.one.model.TokenContainer
import com.example.one.ui.auth.AuthActivity
import com.example.one.ui.favoriteList.FavoriteListActivity
import com.example.one.ui.orderHistory.OrderHistoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment:NikeFragment() {

    lateinit var binding: FragmentProfileBinding
    val viewModel:ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(),OrderHistoryActivity::class.java))
        }

        binding.favoriteListBtn.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (!TokenContainer.token.isNullOrEmpty()){
            binding.emailTv.text=viewModel.userNameLiveData.value
            binding.signInOrSignOutBtn.text=getString(R.string.signOut)
            binding.signInOrSignOutBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_sign_out,0)
            binding.signInOrSignOutBtn.setOnClickListener {
                viewModel.onSignOut()
                checkAuthState()
            }
        }else{
            binding.emailTv.text=getString(R.string.guest_user)
            binding.signInOrSignOutBtn.text=getString(R.string.signIn)
            binding.signInOrSignOutBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_sign_in,0)
            binding.signInOrSignOutBtn.setOnClickListener {
                startActivity(Intent(requireContext(),AuthActivity::class.java))
            }
        }
    }
}