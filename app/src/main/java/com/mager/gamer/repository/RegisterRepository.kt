package com.mager.gamer.repository

import com.mager.gamer.data.model.remote.register.RegisterBody
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
import org.json.JSONObject
import javax.inject.Inject


class RegisterRepository @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun register(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        body: RegisterBody
    ) = flow {
        val response = apiService.register(body)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            if (this.errorBody != null) {
                val jsonObject = JSONObject(this.errorBody!!.string())
                val message = jsonObject.getString("message")
                onError(message)
            } else {
                onError(this.message())
            }
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)
}