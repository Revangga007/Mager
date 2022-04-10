package com.mager.gamer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mager.gamer.data.model.remote.postingan.Data
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
)   : ViewModel() {

    val postinganResult = MutableLiveData<List<Data>>()

    suspend fun getAllPost() {
        mainRepository.getPostingan(
            onStart = {},
            onComplete = {},
            onError = {}
        ).collect {
            postinganResult.postValue(it.data)
        }
    }
}
