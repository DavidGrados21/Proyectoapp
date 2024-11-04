package com.example.proyectoappv3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectoappv3.alumnop.MenuFragEstudiantes
import com.example.proyectoappv3.databinding.FragveriBinding

class Fragverificacion : Fragment() {

    private var _binding: FragveriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragveriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinuar.setOnClickListener {
            val intent = Intent(requireActivity(), MenuFragEstudiantes::class.java)
            startActivity(intent)

            Toast.makeText(context, "TODO CORRECTO", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
