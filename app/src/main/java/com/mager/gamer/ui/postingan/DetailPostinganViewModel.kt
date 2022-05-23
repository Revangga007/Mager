package com.mager.gamer.ui.postingan

//import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.komentar.KomentarBody
import com.mager.gamer.data.model.remote.postingan.komentar.KomentarPostinganResponse
import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DetailPostinganViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val likeResult = MutableLiveData<LikePostinganResponse>()
    val postComment = MutableLiveData<KomentarPostinganResponse>()

    suspend fun likePostingan(idPost: Int) {
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

    suspend fun komentarPostingan(
        idPostingan: Int,
        isiKomentar: String
    ) {
        val idUser = MagerSharedPref.userId!!
        val body = KomentarBody(isiKomentar)
        mainRepository.komentar(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { _message.postValue(it) },
            idPostingan,
            idUser,
            body
        ).collect {
            postComment.postValue(it)
        }
    }

}