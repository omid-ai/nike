package com.example.one.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.common.exceptionHandling.Result
import com.example.one.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {

    private val _loginResultLiveData=MutableLiveData<Result<Boolean>>()
    val loginResultLiveData:LiveData<Result<Boolean>> get()=_loginResultLiveData

    fun login(username:String,password:String){
        viewModelScope.launch {
            val tokenResponce=async {
                userRepository.login(username,password)
            }
            try {
                userRepository.onSuccessfulLogin(tokenResponce.await(),username)
                _loginResultLiveData.postValue(Result.Success(true,""))
            }catch (t:Throwable){
                 _loginResultLiveData.postValue(Result.Error(false,""))
            }

        }









//        viewModelScope.async {
//            val tokenResponce=userRepository.login(username,password)
//            userRepository.onSuccessfulLogin(tokenResponce)
//        }
    }
}