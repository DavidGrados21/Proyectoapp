package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PantallaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_principal)

        val btnDocente = findViewById<Button>(R.id.btnDocente)
        val btnEstudiante = findViewById<Button>(R.id.btnEstudiante)

        btnDocente.setOnClickListener {
            val explicitIntent = Intent(this, InicioSesionD::class.java)
            startActivity(explicitIntent)
        }
    }
}