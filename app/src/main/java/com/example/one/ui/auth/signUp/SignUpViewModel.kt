package com.example.one.ui.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.common.exceptionHandling.NikeExceptionMapper
import com.example.one.common.exceptionHandling.Result
import com.example.one.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {

    private val _signUpResultLiveData=MutableLiveData<Result<Boolean>>()
    val signUpResultLiveData:LiveData<Result<Boolean>> get() = _signUpResultLiveData

    fun signUp(username:String,password:String){
        viewModelScope.launch {
            try {
                userRepository.signUp(username,password)
                delay(1000L)
            }catch (t:Throwable){
                EventBus.getDefault().post(NikeExceptionMapper.mapper(t))
                Timber.e("error in signUp process ->$t")
            }
            val tokenResponce=async {
                userRepository.login(username,password)
            }
            try {
                userRepository.onSuccessfulLogin(tokenResponce.await())
                _signUpResultLiveData.postValue(Result.Success(true,""))
            }catch (t:Throwable){
                _signUpResultLiveData.postValue(Result.Error(false,""))
                Timber.e("error in saving token->$t")
            }
        }
    }
}