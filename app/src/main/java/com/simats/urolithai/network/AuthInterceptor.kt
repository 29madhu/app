package com.simats.urolithai.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        // Don't add token for login endpoint
        if (!request.url.encodedPath.contains("login-otp/")) {
            tokenManager.getAccessToken()?.let {
                builder.addHeader("Authorization", "Bearer $it")
            }
        }

        builder.addHeader("Content-Type", "application/json")
        
        return chain.proceed(builder.build())
    }
}
