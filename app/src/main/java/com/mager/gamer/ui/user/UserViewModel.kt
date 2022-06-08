package com.mager.gamer.ui.user

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.postingan.get.PostinganResponse
import com.mager.gamer.data.model.remote.user.detail.UserDetailResponse
import com.mager.gamer.data.model.remote.user.follow.FollowResponse
import com.mager.gamer.data.model.remote.user.getfollowers.GetFolResponse
import com.mager.gamer.data.model.remote.user.getfollowing.GetFollowingResponse
import com.mager.gamer.repository.MainRepository
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val userDetail = MutableLiveData<UserDetailResponse>()
    val followingResponse = MutableLiveData<GetFollowingResponse>()
    val followerResponse = MutableLiveData<GetFolResponse>()
    val postinganResponse = MutableLiveData<PostinganResponse>()
    val followResponse = MutableLiveData<FollowResponse>()

    suspend fun getAllPost(
        idUser: Int
    ) {
        userRepository.getAllPost(
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
            postinganResponse.postValue(it)
        }
    }

    suspend fun getUserDetail(
        idUser: Int
    ) {
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
        idUser: Int
    ) {

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
        idUser: Int
    ) {
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
    suspend fun followUser(
        idFollower: Int,
        idFollowing: Int
    ) {
        userRepository.postFollow(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            idFollower,
                    idFollowing
        ).collect {
            followResponse.postValue(it)
        }
    }
}