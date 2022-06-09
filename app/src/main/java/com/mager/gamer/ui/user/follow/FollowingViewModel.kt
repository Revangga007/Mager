package com.mager.gamer.ui.user.follow

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.user.getfollowing.GetFollowingResponse
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    val followingResponse = MutableLiveData<GetFollowingResponse>()

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

}