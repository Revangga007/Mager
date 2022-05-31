package com.mager.gamer.ui.postingan

//import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.delete.DeleteResponse
import com.mager.gamer.data.model.remote.postingan.edit.EditBody
import com.mager.gamer.data.model.remote.postingan.edit.EditPostResponse
import com.mager.gamer.data.model.remote.postingan.komentar.del.KomentarDelResponse
import com.mager.gamer.data.model.remote.postingan.komentar.get.GetKomenResponse
import com.mager.gamer.data.model.remote.postingan.komentar.post.KomentarBody
import com.mager.gamer.data.model.remote.postingan.komentar.post.KomentarPostinganResponse
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
    val deletePost = MutableLiveData<DeleteResponse>()
    val delKomen = MutableLiveData<KomentarDelResponse>()
    val editPost = MutableLiveData<EditPostResponse>()
    val getKomen = MutableLiveData<GetKomenResponse>()

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
        mainRepository.createKomentar(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { _message.postValue(it) },
            idUser,
            idPostingan,
            body
        ).collect {
            postComment.postValue(it)
        }
    }

    suspend fun deleteThisPost(
        idPost: Int
    ) {
        mainRepository.delPost(
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
        ).collect {
            deletePost.postValue(it)
        }
    }
    suspend fun deleteKomentar(
        idKomentar: Int
    ) {
        mainRepository.delKomentar(
            onStart = {
                showLoading()
            },
            onComplete = {
                hideLoading()
            },
            onError = {
                _message.postValue(it)
            },
            idKomentar,
        ).collect {
            delKomen.postValue(it)
        }
    }

    suspend fun getKomentarPostingan(
        idPostingan: Int,
    ) {
        mainRepository.getKomenPost(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { _message.postValue(it) },
            idPostingan
        ).collect {
            getKomen.postValue(it)
        }
    }

    suspend fun editPost(
        idPostingan: Int,
        body: EditBody
    ) {
        mainRepository.editPost(
            onStart = {
                showLoading()
            },
            onComplete = {
                hideLoading()
            },
            onError = {
                _message.postValue(it)
            },
            idPostingan,
            body
        ).collect {
            editPost.postValue(it)
        }
    }

}