package com.mager.gamer.ui.komunitas

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.komunitas.get.Content
import com.mager.gamer.data.model.remote.komunitas.get.Data
import com.mager.gamer.repository.KomunitasRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class KomunitasViewModel @Inject constructor(
    private val komunitasRespository: KomunitasRespository
) : BaseViewModel() {

    val komunitasResult = MutableLiveData<List<Content>>()

    suspend fun getRecomendasiKomunitas() {
        komunitasRespository.getRekomendasiKomunitas(
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
            komunitasResult.postValue(it.data.content)
        }
    }
}