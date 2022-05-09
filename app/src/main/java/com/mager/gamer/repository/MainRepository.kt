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
        //val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjZWx6YSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cHM6Ly9tYXJrYXMtZ2FtZXIuaGVyb2t1YXBwLmNvbS9tYWdlci9sb2dpbiIsImV4cCI6MTY1MDkxNjg3N30.NQ0zPW64ny8zQ-6QHuOXJZxI2Q58vrk2-9Lnr6ECNmE"
        val response = apiService.getPostingan(100, 0, null, null, null, null)
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
        idPost: Int,
        idUser: Int
    ) = flow {
        // val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjZWx6YSIsInJvbGVzIjpbXSwiaXNzIjoiaHR0cHM6Ly9tYXJrYXMtZ2FtZXIuaGVyb2t1YXBwLmNvbS9tYWdlci9sb2dpbiIsImV4cCI6MTY1MDkxNjg3N30.NQ0zPW64ny8zQ-6QHuOXJZxI2Q58vrk2-9Lnr6ECNmE"
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
}

