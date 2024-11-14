package com.example.proyectoappv3.alumnop

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.SQLite.DB.DBAlumnos
import com.example.proyectoappv3.R

class RegistroE : AppCompatActivity() {
    private lateinit var dbHelper: DBAlumnos
    private var selectedCareer: String? = null
    private var selectedCycle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_e)
        val email21 = intent.getStringExtra("email")

        dbHelper = DBAlumnos(this)

        val spinner1: Spinner = findViewById(R.id.SpCarrera2)
        val spinner2: Spinner = findViewById(R.id.SpCiclo2)
        val etFechaNacimiento = findViewById<EditText>(R.id.TxtFecha2)
        val nombre = findViewById<EditText>(R.id.txtNombre2)
        val correo = findViewById<EditText>(R.id.txtCorreo2)
        val clave = findViewById<EditText>(R.id.TxtPass2)
        val registrar = findViewById<Button>(R.id.btnRegistrar)
        correo.setText(email21)


        // Cuando el EditText es presionado, se muestra el DatePicker
        etFechaNacimiento.setOnClickListener {
            mostrarDatePicker(etFechaNacimiento)
        }

        val options1 = listOf(
            "Negocios",
            "Administración",
            "Contabilidad y Finanzas",
            "Administración y Marketing",
            "Administración y Servicios Turísticos",
            "Administración Bancaria y Financiera",
            "Administración y Negocios Internacionales",
            "Economía y Negocios Internacionales",
            "Economía",
            "Gastronomía y Gestión de Restaurantes",
            "Administración y Gestión Empresarial",
            "Negocios Internacionales",
            "Marketing Internacional",
            "Ingeniería",
            "Ingeniería Civil",
            "Ingeniería de Sistemas Computacionales",
            "Ingeniería Industrial",
            "Ingeniería Agroindustrial",
            "Ingeniería Ambiental",
            "Ingeniería de Minas",
            "Ingeniería Electrónica",
            "Ingeniería Geológica",
            "Ingeniería Mecatrónica",
            "Ingeniería Empresarial",
            "Ingeniería de Sistemas y Redes",
            "Arquitectura y Diseño",
            "Arquitectura y Diseño de Interiores",
            "Arquitectura y Urbanismo",
            "Diseño Industrial",
            "Derecho y Ciencias Políticas",
            "Derecho",
            "Ciencias de la Salud",
            "Psicología",
            "Enfermería",
            "Nutrición y Dietética",
            "Obstetricia",
            "Terapia Física y Rehabilitación",
            "Medicina Humana",
            "Comunicaciones",
            "Comunicación Audiovisual en Medios Digitales",
            "Comunicación y Diseño Gráfico",
            "Comunicación y Periodismo",
            "Comunicación y Publicidad",
            "Comunicación",
            "Comunicación y Marketing Digital"
        )

        val options2 = listOf(
            "Ciclo 1",
            "Ciclo 2",
            "Ciclo 3",
            "Ciclo 4",
            "Ciclo 5",
            "Ciclo 6",
            "Ciclo 7",
            "Ciclo 8",
            "Ciclo 9",
            "Ciclo 10"
        )

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options1)
        spinner1.adapter = adapter1

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options2)
        spinner2.adapter = adapter2

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCareer = options1[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCycle = options2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        registrar.setOnClickListener {
            val name = nombre.text.toString()
            val email = correo.text.toString()
            val password = clave.text.toString()
            val fechaNacimiento = etFechaNacimiento.text.toString()

            // Llamar al método insertUser
            try {
                dbHelper.insertAlumno(name, password, email, selectedCareer!!, selectedCycle!!, fechaNacimiento)
                Toast.makeText(this, "Usuario insertado correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioSesionE::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al insertar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
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