package com.mager.gamer.ui.komunitas

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.komunitas.get.Content
import com.mager.gamer.data.model.remote.komunitas.join.JoinCommunityResponse
import com.mager.gamer.repository.KomunitasRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class KomunitasViewModel @Inject constructor(
    private val repository: KomunitasRespository
) : BaseViewModel() {

    val allCommunity = MutableLiveData<List<Content>>()
    val joinResponse = MutableLiveData<JoinCommunityResponse>()

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
            }
        ).collect {
            allCommunity.postValue(it.data.content)
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
}