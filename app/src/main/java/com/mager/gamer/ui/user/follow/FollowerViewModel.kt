package com.mager.gamer.ui.user.follow

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.user.getfollowers.GetFolResponse
import com.mager.gamer.repository.MainRepository
import com.mager.gamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    val followerResponse = MutableLiveData<GetFolResponse>()

    suspend fun getAllFollower(
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

}