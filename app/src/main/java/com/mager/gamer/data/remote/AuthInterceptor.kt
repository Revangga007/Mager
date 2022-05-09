package com.mager.gamer.data.remote

import com.mager.gamer.data.local.MagerSharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(

) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = MagerSharedPref.userToken
        val builder = chain.request().newBuilder()

        if (token != null) builder.addHeader("Authorization", "Bearer $token")

        return chain.proceed(builder.build())
    }
}