package com.example.proyectoappv3.fragDocente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectoappv3.R
import com.example.proyectoappv3.UserSession
import com.squareup.picasso.Picasso

class Frag2D : Fragment() {

    private lateinit var imagenUsuario: ImageView
    private lateinit var txtNombre: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag2d, container, false)

        imagenUsuario = view.findViewById(R.id.profile_image)
        txtNombre = view.findViewById(R.id.profesor)
        txtFecha = view.findViewById(R.id.fecha)
        txtCorreo = view.findViewById(R.id.correo)
        txtTelefono = view.findViewById(R.id.telefono)

        val profe = UserSession.currentTeacher

        txtNombre.text = "Profesor: ${profe?.nombre}"
        txtFecha.text = "Fecha: ${profe?.fechaNacimiento}"
        txtCorreo.text = "Correo: ${profe?.correo}"
        txtTelefono.text = "Teléfono: ${profe?.telefono}"

        val imageUrl = profe?.foto ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqVg_URh9Mvrm3NYaTlCUyiM7r382ohELc1g&s"


        // Carga de imagen
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.placeholder) // Asegúrate de tener un drawable de marcador de posición
            .into(imagenUsuario)


        return view
    }
}