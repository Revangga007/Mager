package com.mager.gamer.ui.postingan

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
//import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DetailPostinganViewModel @Inject constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {
    val likeResult = MutableLiveData<LikePostinganResponse>()

    suspend fun likePostingan(idPost:Int) {
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
            likeResult.postValue(it)
        }
    }
}