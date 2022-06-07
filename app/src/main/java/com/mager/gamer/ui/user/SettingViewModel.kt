package com.mager.gamer.ui.user

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.edit.EditBody
import com.mager.gamer.data.model.remote.user.edit.EditUserBody
import com.mager.gamer.data.model.remote.user.edit.EditUserResponse
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    val editUserResponse = MutableLiveData<EditUserResponse>()

    suspend fun editUser(
        body: EditUserBody
    ) {
        val idUser = MagerSharedPref.userId!!
        userRepository.userEdit(
            onStart = {
                showLoading()
            },
            onComplete = {
                hideLoading()
            },
            onError = {
                _message.postValue(it)
            },
            idUser,
            body
        ).collect {
            editUserResponse.postValue(it)
        }
    }
}