package com.mager.gamer.repository

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
        val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0eWFoIiwicm9sZXMiOltdLCJpc3MiOiJodHRwczovL21hcmthcy1nYW1lci5oZXJva3VhcHAuY29tL21hZ2VyL2xvZ2luIiwiZXhwIjoxNjUwNzI5NDYwfQ.TILffytmrTuzBAxTpiKweajZEtqWksKt1mOzw7RnNrc"
        val response = apiService.getPostingan(100, 0, null, null, null, null, token)
        response.suspendOnSuccess {
            emit(this.data)
        }.onError {
            onError(this.message())
        }.onException {
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
        idPost: Int
    ) = flow {
        val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0eWFoIiwicm9sZXMiOltdLCJpc3MiOiJodHRwczovL21hcmthcy1nYW1lci5oZXJva3VhcHAuY29tL21hZ2VyL2xvZ2luIiwiZXhwIjoxNjUwNzI5NDYwfQ.TILffytmrTuzBAxTpiKweajZEtqWksKt1mOzw7RnNrc"
        val response = apiService.likePostingan(idPost,1, token)
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

