package com.simats.urolithai.network

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(

    @SerializedName("user_id")
    val userId: String

)