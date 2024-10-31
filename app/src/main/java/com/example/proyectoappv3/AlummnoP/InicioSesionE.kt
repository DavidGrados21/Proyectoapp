package com.example.proyectoappv3.AlummnoP

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.DB.DBAlumnos
import com.example.proyectoappv3.R
import com.example.proyectoappv3.RecuperarClave

class InicioSesionE : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_sesion_e)

        val txtCorreo = findViewById<EditText>(R.id.TxtCorreo)
        val txtPassword = findViewById<EditText>(R.id.TxtPass)

        val registrarte = findViewById<TextView>(R.id.txtRegistrarte)
        val olvidastes = findViewById<TextView>(R.id.txtOlvidastes)
        val btnLogin = findViewById<TextView>(R.id.btnLogin)

        registrarte.setOnClickListener {
            val explicitIntent = Intent(this, RegistroE::class.java)
            startActivity(explicitIntent)
        }

        olvidastes.setOnClickListener {
            val explicitIntent = Intent(this, RecuperarClave::class.java)
            startActivity(explicitIntent)
        }

        btnLogin.setOnClickListener {
            val nombreDeUsuario = txtCorreo.text.toString()
            val clave = txtPassword.text.toString()

            verificarLogin(nombreDeUsuario, clave)
        }

    }

    private fun verificarLogin(nombreDeUsuario: String, clave: String) {
        val dbJugadores = DBAlumnos(this)

        if (nombreDeUsuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            val loginExitoso = dbJugadores.verificarLogin(nombreDeUsuario, clave)

            if (loginExitoso) {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, navigation::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}