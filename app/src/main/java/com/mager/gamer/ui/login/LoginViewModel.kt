package com.mager.gamer.ui.login

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    var succesData = MutableLiveData<String>()
    var errorLog = MutableLiveData<String>()

    suspend fun login(username: String, password: String) {
        loginRepository.login(
            onStart = {
                showLoading()
            },
            onComplete = {
                hideLoading()
            },
            onError = {
                errorLog.postValue(it.message)
            },
            username = username,
            password = password
        ).collect {
            succesData.postValue("SUKSES LOGIN")
            MagerSharedPref.isLoggedIn = true
            MagerSharedPref.userToken = it.accessToken
            MagerSharedPref.refreshToken = it.refreshToken
            MagerSharedPref.userEmail = it.username
            MagerSharedPref.userId = it.idUser.toInt()
        }
    }
}