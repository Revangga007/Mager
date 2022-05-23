package com.mager.gamer.ui.register

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.register.RegisterBody
import com.mager.gamer.data.model.remote.register.RegisterResponse
import com.mager.gamer.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : BaseViewModel() {

    val register = MutableLiveData<RegisterResponse>()

    suspend fun register(
        email: String,
        gender: String,
        nama: String,
        password: String,
        username: String
    ) {
        val body = RegisterBody("", email, gender, nama, password, username)
        registerRepository.register(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { _message.postValue(it) },
            body
        ).collect {
            register.postValue(it)
        }
    }
}