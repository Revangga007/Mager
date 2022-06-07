package com.mager.gamer.ui.login

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.password.ForgetPassBody
import com.mager.gamer.data.model.remote.password.ForgetPassResponse
import com.mager.gamer.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgetPassViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    val submit = MutableLiveData<ForgetPassResponse>()

    suspend fun submitEmail(
       email: String
    ) {
        val body = ForgetPassBody(email)
        loginRepository.forgetPass(
            onStart = {
                showLoading()
            },
            onComplete = {
                hideLoading()
            },
            onError = {
                _message.postValue(it)
            },
            body
        ).collect {
            submit.postValue(it)
        }
    }
}