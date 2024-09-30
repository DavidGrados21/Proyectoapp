package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InicioSesionE : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_sesion_e)

        val registrarte = findViewById<TextView>(R.id.txtRegistrarte)
        val olvidastes = findViewById<TextView>(R.id.txtOlvidastes)

        registrarte.setOnClickListener {
            val explicitIntent = Intent(this, RegistroE::class.java)
            startActivity(explicitIntent)
        }

        olvidastes.setOnClickListener {
            val explicitIntent = Intent(this, RecuperarClave::class.java)
            startActivity(explicitIntent)
        }
    }
}