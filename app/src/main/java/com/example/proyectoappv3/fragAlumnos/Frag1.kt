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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Frag1 : Fragment() {

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var textViewHours: TextView
    private lateinit var textViewMinutes: TextView
    private lateinit var imagenUsuario: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista del fragmento
        val view = inflater.inflate(R.layout.fragment_frag1, container, false)

        // Inicializar las vistas
        txtNombre = view.findViewById(R.id.txtNombre)
        txtCorreo = view.findViewById(R.id.txtCorreo)
        imagenUsuario = view.findViewById(R.id.imagenUsuario)
        textViewHours = view.findViewById(R.id.textViewHours)
        textViewMinutes= view.findViewById(R.id.textViewMinutes)

        val calendar = Calendar.getInstance()

        val cardViewQR: CardView = view.findViewById(R.id.cardViewQR)
        cardViewQR.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerE, FragScaner())
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

        val hourFormat = SimpleDateFormat("hh", Locale.getDefault())
        val currentHour = hourFormat.format(calendar.time)

        val minuteFormat = SimpleDateFormat("mm ", Locale.getDefault())
        val currentMinute = minuteFormat.format(calendar.time)

        textViewHours.text = "$currentHour horas"
        textViewMinutes.text = "$currentMinute minutos"

        return view
    }
}


