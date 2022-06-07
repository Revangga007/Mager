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
    val editPostResponse = MutableLiveData<EditPostResponse>()

    suspend fun editPost(
        idPostingan: Int,
        text: String
    ) {
        val body = EditBody(text, true)
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
            editPostResponse.postValue(it)
        }
    }
}