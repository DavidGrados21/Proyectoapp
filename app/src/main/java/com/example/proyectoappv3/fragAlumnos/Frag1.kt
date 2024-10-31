package com.example.proyectoappv3.fragAlumnos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.proyectoappv3.R

class Frag1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_frag1, container, false)

        // Configurar el CardView para navegar a FragScaner
        val cardViewQR: CardView = view.findViewById(R.id.cardViewQR)
        cardViewQR.setOnClickListener {
            // Realizar transacción directa al fragmento FragScaner
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragScaner())
                .addToBackStack(null)  // Agregar a la pila para permitir volver atrás
                .commit()
        }

        return view
    }
}
