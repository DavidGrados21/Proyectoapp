package com.example.proyectoappv3.SendGrid

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SendGridService {
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json"
    )
    @POST("v3/mail/send")
    fun sendEmail(@Body email: SendGridEmail): Call<Void>

}