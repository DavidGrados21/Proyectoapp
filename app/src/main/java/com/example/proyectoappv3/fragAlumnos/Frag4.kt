package com.example.proyectoappv3.fragAlumnos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBAsistencia
import com.example.proyectoappv3.SQLite.DB.DBCurso
import com.example.proyectoappv3.UserSession
import kotlin.random.Random

class Frag4 : Fragment() {

    private lateinit var cursosContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_frag4, container, false)

        // Inicializa el contenedor para los cursos
        val cursosContainer: LinearLayout = view.findViewById(R.id.cursos_container)


        val alumno = UserSession.currentUser
        val dbCurso = DBCurso(requireContext())
        val dbAsistencia = DBAsistencia(requireContext())

        val estudiante = alumno?.nombre ?: "Nombre no disponible"
        val cursos = dbCurso.obtenerCursosPorNombreUsuario(estudiante)


        // Agrega un CardView para cada curso
        for (curso in cursos) {
            val cardView = CardView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                cardElevation = 4f
                radius = 8f
                setPadding(16, 16, 16, 16)
            }

            // Crea un LinearLayout dentro del CardView
            val layout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 16, 16, 16)
            }

            // Crea los TextViews para el curso y asistencias
            val tvCurso = TextView(requireContext()).apply {
                text = "Curso: $curso"
                textSize = 16f
            }

            val cantidadAsistencias = dbAsistencia.obtenerCantidadAsistencias(estudiante, curso)

            val tvAsistencias = TextView(requireContext()).apply {
                text = "Asistencias: $cantidadAsistencias"
                textSize = 16f
            }

            layout.addView(tvCurso)
            layout.addView(tvAsistencias)

            cardView.addView(layout)

            cursosContainer.addView(cardView)
        }

        return view
    }
}