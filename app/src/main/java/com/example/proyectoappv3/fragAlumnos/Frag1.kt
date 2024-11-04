package com.example.proyectoappv3.fragAlumnos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.proyectoappv3.R
import com.example.proyectoappv3.UserSession
import com.squareup.picasso.Picasso


class Frag1 : Fragment() {

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var imagenUsuario: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista del fragmento
        val view = inflater.inflate(R.layout.fragment_frag1, container, false)

        // Inicializar las vistas
        txtNombre = view.findViewById(R.id.txtNombre)
        txtCorreo = view.findViewById(R.id.txtCorreo) // Asegúrate de que este TextView esté en tu layout
        imagenUsuario = view.findViewById(R.id.imagenUsuario) // Asegúrate de que este ImageView esté en tu layout

        // Configurar el CardView para navegar a FragScaner
        val cardViewQR: CardView = view.findViewById(R.id.cardViewQR)
        cardViewQR.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragScaner())
                .addToBackStack(null)
                .commit()
        }

        // Obtener los datos del usuario de la sesión
        val alumno = UserSession.currentUser
        txtNombre.text = alumno?.nombre ?: "Nombre no disponible"
        txtCorreo.text = alumno?.correo ?: "Correo no disponible"
        val imageUrl= alumno?.foto ?: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqVg_URh9Mvrm3NYaTlCUyiM7r382ohELc1g&s"

        Picasso.get()
            .load(imageUrl)
            .into(imagenUsuario)

        return view // Devolver la vista inflada
    }
}


