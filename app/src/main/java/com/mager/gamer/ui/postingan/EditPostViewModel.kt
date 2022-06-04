package com.mager.gamer.ui.postingan

import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.postingan.edit.EditBody
import com.mager.gamer.data.model.remote.postingan.edit.EditPostResponse
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel@Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val editPost = MutableLiveData<EditPostResponse>()

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