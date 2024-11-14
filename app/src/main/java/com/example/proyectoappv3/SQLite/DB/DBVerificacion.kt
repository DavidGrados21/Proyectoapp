package com.example.proyectoappv3.SQLite.DB

import android.content.ContentValues
import android.content.Context
import com.example.proyectoappv3.SendGrid.Content
import com.example.proyectoappv3.SendGrid.EmailAddress
import com.example.proyectoappv3.SendGrid.Personalization
import com.example.proyectoappv3.SendGrid.SendGridClient
import com.example.proyectoappv3.SendGrid.SendGridEmail
import retrofit2.Call

class DBVerificacion (context: Context) : DBHelper(context){
    private val sendGridService = SendGridClient.instance

    fun sendVerificationEmail(toEmail: String, code: String) {
        val email = SendGridEmail(
            personalizations = listOf(
                Personalization(to = listOf(EmailAddress(email = toEmail)))
            ),
            from = EmailAddress(email = "n00287801@upn.pe"),
            subject = "Código de Verificación",
            content = listOf(Content(type = "text/plain", value = "Tu código de verificación es: $code"))
        )

        val call = sendGridService.sendEmail(email)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    println("Correo de verificación enviado correctamente.")
                } else {
                    println("Error al enviar el correo: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Fallo en el envío del correo: ${t.message}")
            }
        })
    }
}