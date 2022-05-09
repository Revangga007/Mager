package com.mager.gamer.repository

import com.google.gson.Gson
import com.mager.gamer.data.model.remote.login.LoginBody
import com.mager.gamer.data.model.remote.login.LoginErorResponse
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

class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun login(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (LoginErorResponse) -> Unit,
        username: String,
        password: String
    ) = flow {
        val response = apiService.login(username, password)
        response.suspendOnSuccess {
            emit(this.data)
        }.onError {
            val err: LoginErorResponse = Gson().fromJson(errorBody?.string(),LoginErorResponse::class.java)
            onError(err)
            Timber.e(this.message())
        }.onException {
            Timber.e(this.message())
            // onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)
}