package com.example.proyectoappv3.profesor

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.PantallaPrincipal
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBProfesores

class Registro : AppCompatActivity() {
    private lateinit var dbHelper: DBProfesores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        dbHelper = DBProfesores(this)

        val menu = findViewById<TextView>(R.id.txtsubprincipal2)
        val etFechaNacimiento = findViewById<EditText>(R.id.TxtFecha2)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val nombre = findViewById<EditText>(R.id.txtNombre2)
        val correo = findViewById<EditText>(R.id.txtCorreo2)
        val clave = findViewById<EditText>(R.id.TxtPass2)
        val telefono = findViewById<EditText>(R.id.txtNumero2)

        btnRegistrar.setOnClickListener {
            val name = nombre.text.toString()
            val email = correo.text.toString()
            val password = clave.text.toString()
            val telefono = telefono.text.toString()
            val fechaNacimiento = etFechaNacimiento.text.toString()

            // Llamar al método insertUser
            try {
                dbHelper.insertProfesor(name, password, email, telefono , fechaNacimiento)
                Toast.makeText(this, "Usuario insertado correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioSesionD::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al insertar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        etFechaNacimiento.setOnClickListener {
            mostrarDatePicker(etFechaNacimiento)
        }

        menu.setOnClickListener {
            val explicitIntent = Intent(this, PantallaPrincipal::class.java)
            startActivity(explicitIntent)
        }
    }
    private fun mostrarDatePicker(etFecha: EditText) {
        // Obtener la fecha actual
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        // Crear el DatePickerDialog
        val datePicker = DatePickerDialog(
            this,
            { _, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                // Al seleccionar una fecha, mostrarla en el EditText
                val fechaSeleccionada = "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                etFecha.setText(fechaSeleccionada)
            },
            anio, mes, dia
        )

        // Mostrar el DatePickerDialog
        datePicker.show()
    }
}