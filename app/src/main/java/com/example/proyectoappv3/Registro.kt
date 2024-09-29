package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        val menu = findViewById<TextView>(R.id.txtsubprincipal2)

        menu.setOnClickListener {
            val explicitIntent = Intent(this, PantallaPrincipal::class.java)
            startActivity(explicitIntent)
        }
    }
}