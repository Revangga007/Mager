package com.mager.gamer.ui.komunitas

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.komunitas.get.Komunitas
import com.mager.gamer.data.model.remote.komunitas.join.JoinCommunityResponse
import com.mager.gamer.repository.KomunitasRespository
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class KomunitasViewModel @Inject constructor(
    private val repository: KomunitasRespository,
    private val mainRepository: MainRepository
) : BaseViewModel() {

    val allCommunity = MutableLiveData<List<Komunitas>>()
    val joinResponse = MutableLiveData<JoinCommunityResponse>()
    val joinedCommunity = MutableLiveData<List<Komunitas>>()
    val likeClickPosition = MutableLiveData<Int>()

    suspend fun getAllCommunity() {
        repository.getAllCommunity(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
        ).collect {
            allCommunity.postValue(it.data.content)
        }
    }

    suspend fun getJoinedCommunity() {
        val idUser = MagerSharedPref.userId ?: -1
        repository.getJoinedCommunity(
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
            joinedCommunity.postValue(it.data.content.map { community ->
                community.komunitas.joined = true
                community.komunitas
            })
        }
    }

    suspend fun joinCommunity(idCommunity: Int) {
        val idUser = MagerSharedPref.userId ?: -1
        repository.joinCommunity(
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = { _message.postValue(it) },
            idUser,
            idCommunity
        ).collect {
            joinResponse.postValue(it)
        }
    }

    suspend fun likeUnlikePost(idPost: Int, position: Int) {
        val idUser = MagerSharedPref.userId!!
        mainRepository.likePostingan(
            onStart = {
                showLoading()
            },
            onComplete = {
                hideLoading()
            },
            onError = {
                _message.postValue(it)
            },
            idPost,
            idUser
        ).collect {
            likeClickPosition.postValue(position)
        }
    }
}