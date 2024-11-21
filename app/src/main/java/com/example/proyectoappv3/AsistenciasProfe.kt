package com.example.proyectoappv3

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.proyectoappv3.SQLite.DB.DBAlumnos
import com.example.proyectoappv3.SQLite.DB.DBAsistencia

class AsistenciasProfe : AppCompatActivity() {

    private lateinit var dbh: DBAlumnos
    private lateinit var dbAsistencia: DBAsistencia
    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistencias_profe)

        // Inicializar la base de datos
        dbh = DBAlumnos(this)
        dbAsistencia = DBAsistencia(this)

        val curso = UserSession.currentCourse
        if (curso == null) {
            Toast.makeText(this, "No se seleccionó ningún curso", Toast.LENGTH_SHORT).show()
            return
        }

        id = curso.id
        mostrarAlumnosEnTabla(id)
    }

    fun mostrarAlumnosEnTabla(cursoId: Int) {
        val nombres = dbh.obtenerNombresDeAlumnos(cursoId)
        val dbAlumno = DBAlumnos(this)
        val alumnosTable = findViewById<LinearLayout>(R.id.alumnosTable)
        alumnosTable.removeAllViews()

        if (nombres.isEmpty()) {
            val mensajeTextView = TextView(this)
            mensajeTextView.text = "No hay alumnos registrados para este curso"
            mensajeTextView.textSize = 16f
            mensajeTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
            mensajeTextView.gravity = Gravity.CENTER
            alumnosTable.addView(mensajeTextView)
            return
        }

        for (nombre in nombres) {
            val fila = LinearLayout(this)
            fila.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            fila.orientation = LinearLayout.HORIZONTAL
            fila.setPadding(0, 8, 0, 8)


            val nombreTextView = TextView(this)
            nombreTextView.text = nombre
            nombreTextView.textSize = 14f
            nombreTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
            nombreTextView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            nombreTextView.gravity = Gravity.CENTER

            val idalumno = dbAlumno.getAlumnoIdByName(nombre)

            val cantidadAsistencias = if (idalumno != null) {
                dbAsistencia.getCantidadAsistenciasAlumnoCurso(idalumno, id)
            } else {
                0
            }

            val asistenciaTextView = TextView(this)
            asistenciaTextView.text = cantidadAsistencias.toString()
            asistenciaTextView.textSize = 14f
            asistenciaTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
            asistenciaTextView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            asistenciaTextView.gravity = Gravity.CENTER

            val cnatidadFaltas = 16 - cantidadAsistencias

            // Crear el TextView para las faltas (vacío)
            val faltasTextView = TextView(this)
            faltasTextView.text = cnatidadFaltas.toString()
            faltasTextView.textSize = 14f
            faltasTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
            faltasTextView.layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            faltasTextView.gravity = Gravity.CENTER

            // Agregar los TextViews a la fila
            fila.addView(nombreTextView)
            fila.addView(asistenciaTextView)
            fila.addView(faltasTextView)

            // Agregar la fila al LinearLayout de la tabla
            alumnosTable.addView(fila)
        }
    }
}
