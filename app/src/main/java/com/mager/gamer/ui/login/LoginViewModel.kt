package com.mager.gamer.ui.login

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.user.detail.UserDetailResponse
import com.mager.gamer.repository.LoginRepository
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    var succesData = MutableLiveData<String>()
    var errorLog = MutableLiveData<String>()
    var detailProfile = MutableLiveData<UserDetailResponse>()

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
            MagerSharedPref.username = it.username
            MagerSharedPref.userId = it.idUser.toInt()
            userRepository.userDetail(
                onStart = {
                    showLoading()
                },
                onComplete = {
                    hideLoading()
                },
                onError = {
                    errorLog.postValue(it)
                },
                it.idUser.toInt()
            ).collect{ responseUser ->
                MagerSharedPref.fotoProfile = responseUser.data.fotoProfile
                MagerSharedPref.fullName = responseUser.data.nama
                MagerSharedPref.userEmail = responseUser.data.email
            }
        }
    }
}
