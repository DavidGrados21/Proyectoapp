package com.example.proyectoappv3

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        val menu = findViewById<TextView>(R.id.txtsubprincipal2)
        val etFechaNacimiento = findViewById<EditText>(R.id.TxtFecha2)

        // Cuando el EditText es presionado, se muestra el DatePicker
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