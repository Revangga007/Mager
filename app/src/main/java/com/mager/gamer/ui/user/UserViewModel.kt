package com.mager.gamer.ui.user

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.user.detail.UserDetailResponse
import com.mager.gamer.data.model.remote.user.getfollowers.GetFolResponse
import com.mager.gamer.data.model.remote.user.getfollowing.GetFollowingResponse
import com.mager.gamer.repository.MainRepository
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val userDetail = MutableLiveData<UserDetailResponse>()
    val followingResponse = MutableLiveData<GetFollowingResponse>()
    val followerResponse = MutableLiveData<GetFolResponse>()
    val postinganResult = MutableLiveData<List<Data>>()

    suspend fun getAllPost() {
        mainRepository.getPostingan(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            }
        ).collect {
            postinganResult.postValue(it.data)
        }
    }

    suspend fun getUserDetail(
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
        userRepository.getFollower(
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
            followerResponse.postValue(it)
        }
    }
    suspend fun getAllFollowing(
    ) {
        val idUser = MagerSharedPref.userId!!
        userRepository.getFollowing(
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
            followingResponse.postValue(it)
        }
    }
}