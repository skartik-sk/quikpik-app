package com.example.quikpik.data.remort.others

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() :Interceptor {
    @Inject
    lateinit var tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val cookie = "access_token=${tokenManager.getToken()}"
        val newRequest = request.addHeader("Cookie", cookie).build()
        return chain.proceed(newRequest)
    }
}