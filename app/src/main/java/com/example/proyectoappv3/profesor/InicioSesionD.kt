package com.example.proyectoappv3.profesor

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.R
import com.example.proyectoappv3.RecuperarClave
import com.example.proyectoappv3.SQLite.DB.DBProfesores
import com.example.proyectoappv3.UserSession

class InicioSesionD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_sesion_d)

        val txtCorreo = findViewById<EditText>(R.id.TxtCorreo)
        val txtPassword = findViewById<EditText>(R.id.TxtPass)

        val regsitro = findViewById<TextView>(R.id.txtRegistrarte)
        val olvidastes = findViewById<TextView>(R.id.txtOlvidastes)
        val btnlogin = findViewById<TextView>(R.id.btnLogin)

        regsitro.setOnClickListener {
            val explicitIntent = Intent(this, Registro::class.java)
            startActivity(explicitIntent)
        }

        btnlogin.setOnClickListener {
            val correo = txtCorreo.text.toString()
            val clave = txtPassword.text.toString()

            verificarLogin(correo, clave)
        }

        olvidastes.setOnClickListener {
            val explicitIntent = Intent(this, RecuperarClave::class.java)
            startActivity(explicitIntent)
        }

    }

    private fun verificarLogin(correo: String, clave: String) {
        val dbprofe = DBProfesores(this)

        if (correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            val loginExitoso = dbprofe.verificarLogin(correo, clave)

            if (loginExitoso) {
                val profe = dbprofe.obtenerDatosProfesor(correo)
                UserSession.currentTeacher = profe

                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MenuFragDocente::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }

        }
    }
}