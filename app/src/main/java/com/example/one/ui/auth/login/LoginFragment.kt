package com.example.one.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.one.R
import com.example.one.common.base.NikeFragment
import com.example.one.common.exceptionHandling.Result
import com.example.one.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:NikeFragment() {

    lateinit var binding: FragmentLoginBinding
    val viewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            viewModel.login(binding.loginEmailEt.text.toString(),binding.loginPasswordEt.text.toString())
            viewModel.loginResultLiveData.observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Success->requireActivity().finish()
                }
            }
        }

        binding.goToSignUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}