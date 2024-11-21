package com.example.proyectoappv3.com.example.proyectoappv3.alumnop

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBAlumnos
import com.example.proyectoappv3.UserSession
import com.example.proyectoappv3.alumnop.InicioSesionE
import java.util.Calendar

class EditDatosAlumno : AppCompatActivity() {

    private lateinit var dbHelper: DBAlumnos
    private var selectedCareer: String? = null
    private var selectedCycle: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        dbHelper = DBAlumnos(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_datos_alumno)


        val alumno = UserSession.currentUser
        val txtNombre = findViewById<TextView>(R.id.txtNombre2)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo2)
        val txtPass = findViewById<TextView>(R.id.TxtPass2)
        val txtFecha = findViewById<EditText>(R.id.TxtFecha2)
        val spinner1: Spinner = findViewById(R.id.SpCarrera2)
        val spinner2: Spinner = findViewById(R.id.SpCiclo2)
        val actualizar = findViewById<Button>(R.id.btnRegistrar)

        txtFecha.setOnClickListener {
            mostrarDatePicker(txtFecha)
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

        actualizar.setOnClickListener {
            val name = txtNombre.text.toString()
            val email = txtCorreo.text.toString()
            val password = txtPass.text.toString()
            val fechaNacimiento = txtFecha.text.toString()
            val id = alumno?.id
            val foto = alumno?.foto

            try {
                dbHelper.actualizarAlumno(id, name, password, email, selectedCareer!!, selectedCycle!!, fechaNacimiento, foto)
                Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioSesionE::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error al actualizar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }


        }

        txtNombre.text = alumno?.nombre
        txtCorreo.text = alumno?.correo
        txtPass.text = alumno?.password
        txtFecha.setText(alumno?.fechaNacimiento)
        selectedCareer = alumno?.carrera
        selectedCycle = alumno?.ciclo
    }
    private fun mostrarDatePicker(etFecha: EditText) {
        // Obtener la fecha actual
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        // Crear el DatePickerDialog
        val datePicker = DatePickerDialog(this,
            { _, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                // Al seleccionar una fecha, mostrarla en el EditText
                val fechaSeleccionada = "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                etFecha.setText(fechaSeleccionada)
            },
            anio, mes, dia
        )


        datePicker.show()
    }

}
