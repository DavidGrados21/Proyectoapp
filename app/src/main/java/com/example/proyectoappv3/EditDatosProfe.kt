package com.example.proyectoappv3.com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBProfesores
import com.example.proyectoappv3.UserSession
import com.example.proyectoappv3.profesor.InicioSesionD

class EditDatosProfe : AppCompatActivity() {

    private lateinit var dbHelper: DBProfesores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_datos_profe)

        dbHelper = DBProfesores(this)

        val profe = UserSession.currentTeacher
        val txtNombre = findViewById<TextView>(R.id.txtNombre2)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo2)
        val txtPass = findViewById<TextView>(R.id.TxtPass2)
        val txtFecha = findViewById<TextView>(R.id.TxtFecha2)
        val txtNumero = findViewById<TextView>(R.id.txtNumero2)
        val btnGC = findViewById<Button>(R.id.btnGC)
        val imageLink = profe?.foto

        txtNombre.text = profe?.nombre
        txtCorreo.text = profe?.correo
        txtPass.text = profe?.password
        txtFecha.text = profe?.fechaNacimiento
        txtNumero.text = profe?.telefono

        btnGC.setOnClickListener {
            val name = txtNombre.text.toString()
            val email = txtCorreo.text.toString()
            val password = txtPass.text.toString()
            val telefono = txtNumero.text.toString()
            val fechaNacimiento = txtFecha.text.toString()
            val id = profe?.id

            try {
                dbHelper.actualizarProfesor(id, name, email, password, fechaNacimiento, telefono, imageLink)
                Toast.makeText(this, "Usuario insertado correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioSesionD::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al insertar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}