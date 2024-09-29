package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioSesionD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_sesion_d)

        val regsitro = findViewById<TextView>(R.id.txtRegistrarte)

        regsitro.setOnClickListener {
            val explicitIntent = Intent(this, Registro::class.java)
            startActivity(explicitIntent)
        }

    }
}