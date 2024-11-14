package com.example.proyectoappv3.SendGrid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SendGridClient {

    private const val BaseUrl = "https://api.sendgrid.com/"

    val instance: SendGridService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SendGridService::class.java)
    }
}