package com.example.proyectoappv3.com.example.proyectoappv3.profesor

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectoappv3.AsistenciasProfe
import com.example.proyectoappv3.R
import com.example.proyectoappv3.UserSession

class OpcionesClase : AppCompatActivity() {

    private lateinit var titulo : TextView
    private lateinit var asistencias : Button
    private lateinit var qr : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones_clase)

        val curso = UserSession.currentCourse

        titulo = findViewById(R.id.textoprincipal)
        asistencias = findViewById(R.id.btnAsistencias)
        qr = findViewById(R.id.btnQR)

        asistencias.setOnClickListener {

            val intent = Intent(this, AsistenciasProfe::class.java)
            startActivity(intent)

        }

        qr.setOnClickListener {
            replaceFragment(FragGenerator())
        }

        titulo.text = curso?.nombreCurso ?: "Curso no disponible"
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerD, fragment)
            .addToBackStack(null)
            .commit()
    }
}