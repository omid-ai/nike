package com.example.one.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.one.model.TokenContainer
import com.example.one.model.repository.order.OrderRepository
import com.example.one.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {

    private val _userNameLiveData=MutableLiveData<String>()
    val userNameLiveData:LiveData<String> get() = _userNameLiveData

    init {
        _userNameLiveData.value=getUserName()
    }

//    val isSignedIn:Boolean
//        get() = TokenContainer.token!=null

    private fun getUserName():String{
        return userRepository.loadEmail()
    }

    fun onSignOut(){
        userRepository.signOut()
    }
}