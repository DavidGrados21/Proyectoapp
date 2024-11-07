package com.example.proyectoappv3.fragAlumnos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBCurso
import com.example.proyectoappv3.UserSession
import com.example.proyectoappv3.fragDocente.FragEditDatos
import com.squareup.picasso.Picasso
import kotlin.random.Random

class Frag2 : Fragment() {

    private lateinit var imagenUsuario: ImageView
    private lateinit var txtNombre: TextView
    private lateinit var txtCarrera: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var txtMaterias: TextView
    private lateinit var txtPromedio: TextView
    private lateinit var txtCiclo: TextView
    private lateinit var btnedit: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_frag2, container, false)

        imagenUsuario = view.findViewById(R.id.profile_image)
        txtNombre = view.findViewById(R.id.estudiante)
        txtCarrera = view.findViewById(R.id.carrera)
        txtCorreo = view.findViewById(R.id.correo)
        txtTelefono = view.findViewById(R.id.telefono)
        txtMaterias = view.findViewById(R.id.materias)
        txtPromedio = view.findViewById(R.id.promedio)
        txtCiclo = view.findViewById(R.id.ciclo)
        btnedit = view.findViewById(R.id.imgedit)

        val alumno = UserSession.currentUser
        val dbCurso = DBCurso(requireContext())

        // Asignación de datos con un valor por defecto
        val estudiante = alumno?.nombre ?: "Nombre no disponible"
        val carrera = alumno?.carrera ?: "Carrera no disponible"
        val correo = alumno?.correo ?: "Correo no disponible"
        val telefono = alumno?.telefono ?: "Teléfono no disponible"
        val ciclo = alumno?.ciclo ?: "Ciclo no disponible"
        val imageUrl = alumno?.foto ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqVg_URh9Mvrm3NYaTlCUyiM7r382ohELc1g&s"
        val promedio = Random.nextInt(15, 21)

        // Obtener y mostrar materias
        val materias = alumno?.let {
            val cursos = dbCurso.obtenerCursosPorNombreUsuario(estudiante)
            cursos.joinToString(", ")
        } ?: "Materias: No disponibles"

        txtMaterias.text = "Materias: $materias"

        // Asignación de TextViews
        txtNombre.text = "Estudiante: $estudiante"
        txtCarrera.text = "Carrera: $carrera"
        txtCorreo.text = "Correo: $correo"
        txtTelefono.text = "Teléfono: $telefono"
        txtPromedio.text = "Promedio: $promedio"
        txtCiclo.text = ciclo

        // Carga de imagen
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.placeholder) // Asegúrate de tener un drawable de marcador de posición
            .into(imagenUsuario)

        btnedit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragEditAlumnno())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}

