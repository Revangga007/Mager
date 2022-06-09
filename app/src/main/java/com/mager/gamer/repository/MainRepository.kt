package com.mager.gamer.repository

import com.google.gson.Gson
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.login.LoginErorResponse
import com.mager.gamer.data.model.remote.postingan.create.CreateErrorResponse
import com.mager.gamer.data.model.remote.postingan.create.CreatePostinganResponse
import com.mager.gamer.data.model.remote.postingan.edit.EditBody
import com.mager.gamer.data.model.remote.postingan.komentar.post.KomentarBody
import com.mager.gamer.data.model.remote.postingan.post.CreatePostBody
import com.mager.gamer.data.remote.ApiService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPostingan(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        val response = apiService.getPostingan(100, 0, null, null, null, null, null)
        response.suspendOnSuccess {
            emit(this.data)
        }.onError {
            Timber.e(this.message())
            when (raw.networkResponse?.code) {
                403 -> {
                    onError("Gagal menampilkan postingan, server bermasalah")
                }
                404 -> {
                    onError("Server tidak ditemukan")
                }
                500 -> {
                    onError("Server bermasalah, silahkan coba lagi nanti")
                }
                else -> {
                    onError(message())
                }
            }
        }.onException {
            Timber.e(this.message())
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun likePostingan(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        idPost: Int,
        idUser: Int
    ) = flow {
        val response = apiService.likePostingan(idPost, idUser)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun createPostingan(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (CreateErrorResponse) -> Unit,
        idUser: Int,
        idKomunitas: Int?,
        body: CreatePostBody
    ) = flow<CreatePostinganResponse> {
        val response = apiService.createPostingan(idUser, idKomunitas, body)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            val err: CreateErrorResponse = Gson().fromJson(errorBody?.string(), CreateErrorResponse::class.java)
            onError(err)
            Timber.e(this.message())
        }.onException {
            Timber.e(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun uploadFileAndGetResult(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        file: MultipartBody.Part,
    ) = flow {
        val response = apiService.uploadFiles(file)
        response.suspendOnSuccess {
            emit(this.data)
        }.onError {
            Timber.e(this.message())
            when (raw.networkResponse?.code) {
                403 -> {
                    onError("Gagal upload file, server bermasalah")
                }
                404 -> {
                    onError("Server tidak ditemukan")
                }
                500 -> {
                    onError("Server bermasalah, silahkan coba lagi nanti")
                }
                else -> {
                    onError(message())
                }
            }
        }.onException {
            Timber.e(this.message())
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun createKomentar(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        idUser: Int,
        idPostingan: Int,
        body: KomentarBody
    ) = flow {
        val response = apiService.komentar(idUser,idPostingan, body)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun delPost(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        idPostingan: Int,
    ) = flow {
        val response = apiService.deletePost(idPostingan)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun delKomentar(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        idKomentar: Int,
    ) = flow {
        val response = apiService.delKomentar(idKomentar)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)


    suspend fun editPost(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        idPostingan: Int,
        body: EditBody
    ) = flow {
        val response = apiService.editPost(idPostingan, MagerSharedPref.userId!!, body)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun getKomenPost(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        idPostingan: Int
    ) = flow {
        val response = apiService.getKomentar(idPostingan)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)
}

