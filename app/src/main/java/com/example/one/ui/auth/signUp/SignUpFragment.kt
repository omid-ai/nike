package com.example.one.ui.auth.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.one.R
import com.example.one.common.base.NikeFragment
import com.example.one.common.exceptionHandling.Result
import com.example.one.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:NikeFragment() {

    lateinit var binding: FragmentSignUpBinding
    val viewModel:SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSignUpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            viewModel.signUp(binding.emailEt.text.toString(),binding.passwordEt.text.toString())
            viewModel.signUpResultLiveData.observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Success-> requireActivity().finish()
                }
            }
        }
    }
}