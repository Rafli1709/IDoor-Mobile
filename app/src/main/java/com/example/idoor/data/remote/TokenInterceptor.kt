package com.example.idoor.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private var token: String) : Interceptor {

    fun setToken(newToken: String) {
        token = newToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestWithToken = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(requestWithToken)
    }
}