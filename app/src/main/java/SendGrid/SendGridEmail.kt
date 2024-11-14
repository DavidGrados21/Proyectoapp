package com.example.proyectoappv3.SendGrid

data class SendGridEmail(
    val personalizations: List<Personalization>,
    val from: EmailAddress,
    val subject: String,
    val content: List<Content>
)

data class Personalization(
    val to: List<EmailAddress>
)

data class EmailAddress(
    val email: String
)

data class Content(
    val type: String,
    val value: String
)