package com.mager.gamer.ui.user

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.user.detail.UserDetailResponse
import com.mager.gamer.data.model.remote.user.getfollowers.GetFolResponse
import com.mager.gamer.data.model.remote.user.getfollowing.GetFollowingResponse
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val userDetail = MutableLiveData<UserDetailResponse>()
    val getALlFollowing = MutableLiveData<GetFollowingResponse>()
    val getAllfollower = MutableLiveData<GetFolResponse>()

    suspend fun userDetail(
    ) {
        val idUser = MagerSharedPref.userId!!
        userRepository.userDetail(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            idUser
        ).collect {
            userDetail.postValue(it)
        }
    }
    suspend fun getAllFollowers(
    ) {
        val idUser = MagerSharedPref.userId!!
        userRepository.userDetail(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            idUser
        ).collect {
            userDetail.postValue(it)
        }
    }
    suspend fun getAllFollowing(
    ) {
        val idUser = MagerSharedPref.userId!!
        userRepository.userDetail(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            idUser
        ).collect {
            userDetail.postValue(it)
        }
    }
}