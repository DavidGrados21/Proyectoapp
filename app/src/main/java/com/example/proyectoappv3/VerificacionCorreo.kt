package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.SQLite.DB.DBVerificacion

class VerificacionCorreo : AppCompatActivity() {

    private lateinit var dbHelper: DBVerificacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificacion_correo)
        val tipo = intent.getStringExtra("tipo")

        dbHelper = DBVerificacion(this)

        val correo = findViewById<EditText>(R.id.txtNombre2)
        val btnverificar = findViewById<Button>(R.id.btnRegistrar)

        btnverificar.setOnClickListener {

            val email = correo.text.toString()

            if (isValidEmail(email)) {
                val code = (100000..999999).random().toString()
                dbHelper.sendVerificationEmail(email, code)
                Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show()

                // Crea el intent con los extras
                val intent = Intent(this, VerificarCode::class.java)
                intent.putExtra("tipo", tipo)
                intent.putExtra("email", email)
                intent.putExtra("verification_code", code)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}