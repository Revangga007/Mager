package com.mager.gamer.ui.komunitas

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.komunitas.join.JoinCommunityResponse
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.postingan.get.PostinganResponse
import com.mager.gamer.repository.KomunitasRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DetailCommunityViewModel @Inject constructor(
    private val repository: KomunitasRespository
) : BaseViewModel() {

    val joinResponse = MutableLiveData<JoinCommunityResponse>()
    val postResponse = MutableLiveData<List<Data>>()

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

    suspend fun getAllPost(idCommunity: Int) {
        repository.getAllPost(
            onStart = { showLoading() },
            onComplete = { hideLoading() },
            onError = { _message.postValue(it) },
            idCommunity
        ).collect {
            postResponse.postValue(it.data)
        }
    }

}