package com.example.proyectoappv3.fragDocente

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBProfesores
import com.example.proyectoappv3.SQLite.DB.DropboxHelper
import com.example.proyectoappv3.UserSession
import com.example.proyectoappv3.profesor.InicioSesionD


class FragEditDatos : Fragment() {
    private lateinit var dbHelper: DBProfesores


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            dbHelper = DBProfesores(requireContext())
            val view = inflater.inflate(R.layout.frag_edit_datos, container, false)

            val profe = UserSession.currentTeacher
            val txtNombre = view.findViewById<TextView>(R.id.txtNombre2)
            val txtCorreo = view.findViewById<TextView>(R.id.txtCorreo2)
            val txtPass = view.findViewById<TextView>(R.id.TxtPass2)
            val txtFecha = view.findViewById<TextView>(R.id.TxtFecha2)
            val txtNumero = view.findViewById<TextView>(R.id.txtNumero2)
            val btnGC = view.findViewById<Button>(R.id.btnGC)
            var imageLink: String? = null

            btnGC.setOnClickListener {
                val name = txtNombre.text.toString()
                val email = txtCorreo.text.toString()
                val password = txtPass.text.toString()
                val telefono = txtNumero.text.toString()
                val fechaNacimiento = txtFecha.text.toString()
                val id = profe?.id

                // Llamar al método insertUser
                try {
                    dbHelper.actualizarProfesor(id, name, email, password, fechaNacimiento, telefono, imageLink)
                    Toast.makeText(requireContext(), "Usuario insertado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), InicioSesionD::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al insertar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            txtNombre.text = profe?.nombre
            txtCorreo.text = profe?.correo
            txtPass.text = profe?.password
            txtFecha.text = profe?.fechaNacimiento
            txtNumero.text = profe?.telefono

            return view
    }

}


