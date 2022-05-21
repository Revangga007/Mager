package com.mager.gamer.ui.komunitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.komunitas.get.Data
import com.mager.gamer.repository.KomunitasRespository
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class KomunitasViewModel @Inject constructor(
    private val komunitasRespository: KomunitasRespository
) : BaseViewModel() {

    val komunitasResult = MutableLiveData<List<Data>>()

    suspend fun getAllKomunitas() {
        komunitasRespository.getKomunitas(
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
            komunitasResult.postValue(it.data)
        }
    }
}