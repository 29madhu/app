package com.simats.urolithai.network

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/register/")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<Unit>

    @POST("api/send-otp/")
    suspend fun sendOtp(
        @Body request: SendOtpRequest
    ): Response<Unit>

    @POST("api/verify-otp/")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<VerifyOtpResponse>

    @POST("api/set-password/")
    suspend fun setPassword(
        @Body request: SetPasswordRequest
    ): Response<Unit>

    @POST("api/login/")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @Multipart
    @POST("upload-report")
    suspend fun uploadReport(
        @Part file: MultipartBody.Part
    ): Response<Unit>

    @GET("chat/history")
    suspend fun getChatHistory(): Response<List<String>>

    @POST("chat/send")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<Unit>

    @GET("patient/dashboard")
    suspend fun getPatientDashboard(): Response<PatientDashboardResponse>

    @GET("doctor/dashboard")
    suspend fun getDoctorDashboard(): Response<DoctorDashboardResponse>
}