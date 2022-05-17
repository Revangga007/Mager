package com.mager.gamer.ui.postingan

import android.webkit.MimeTypeMap
import androidx.lifecycle.MutableLiveData
import com.mager.gamer.base.BaseViewModel
import com.mager.gamer.data.model.remote.upload.UploadResponse
import com.mager.gamer.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BuatPostinganViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val uploadResponse = MutableLiveData<UploadResponse>()

    suspend fun uploadImageToServer(file: File) {
        val fileRequestBody = file.asRequestBody(
            getMimeType(file.path)!!.toMediaType()
        )
        val fileMultiPart = MultipartBody.Part.createFormData(
            "file",
            file.name, fileRequestBody
        )
        mainRepository.uploadFileAndGetResult(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { _message.postValue(it) },
            fileMultiPart
        ).collect {
            uploadResponse.postValue(it)
        }
    }

    suspend fun createPostingan(
        postType: Int,
        postText: String? = null,
        linkLivestream: String? = null,
        files: String? = null
    ) {
        mainRepository.createPostingan(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { _message.postValue(it) },
            postText,
            linkLivestream,
            files
        ).collect {
            uploadResponse.postValue(it)
        }

    }

    private fun getMimeType(path: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}