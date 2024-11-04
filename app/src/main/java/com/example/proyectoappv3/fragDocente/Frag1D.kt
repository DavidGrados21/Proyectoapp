package com.example.proyectoappv3.fragDocente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBCurso
import com.example.proyectoappv3.UserSession
import com.example.proyectoappv3.com.example.proyectoappv3.SQLite.DB.DBHorario

class Frag1D : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.frag1d, container, false)

        // Obtener la referencia al contenedor de las tarjetas
        val containerCursos = view.findViewById<ConstraintLayout>(R.id.container_cursos)

        val dbc = DBCurso(requireContext())
        val dbh = DBHorario(requireContext())
        val profe = UserSession.currentTeacher
        val idp = profe?.id ?: 0

        // Obtener la lista de cursos del profesor
        val listaCursos = dbc.obtenerCursosDeProfesor(idp)

        // Agregar tarjetas por cada curso en la lista
        for (curso in listaCursos) {
            // Inflar la tarjeta
            val cardView = LayoutInflater.from(context).inflate(R.layout.card_layout, containerCursos, false) as CardView
            val txtCurso = cardView.findViewById<TextView>(R.id.txtCurso)
            val txtHora = cardView.findViewById<TextView>(R.id.txtHora)

            // Asignar el texto a las vistas de la tarjeta
            txtCurso.text = "CURSO: $curso"
            val horaInicio = dbh.obtenerHoraInicioCurso(curso)
            txtHora.text = "HORA DE INICIO: $horaInicio" // Aquí puedes asignar la hora real

            // Definir parámetros para el cardView
            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            // Ajustar las restricciones
            if (containerCursos.childCount > 0) {
                val lastCardView = containerCursos.getChildAt(containerCursos.childCount - 1) as CardView
                params.topToBottom = lastCardView.id
                params.setMargins(0, 10, 0, 0) // Margen superior de 10dp
            } else {
                params.topToTop = containerCursos.id
            }

            cardView.layoutParams = params
            cardView.id = View.generateViewId() // Generar un ID único para cada CardView

            // Agregar la tarjeta al ConstraintLayout
            containerCursos.addView(cardView)
        }

        return view
    }
}
