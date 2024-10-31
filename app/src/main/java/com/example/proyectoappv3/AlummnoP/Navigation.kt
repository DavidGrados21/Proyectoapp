package com.example.proyectoappv3.AlummnoP

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.databinding.NavigationBinding
import androidx.fragment.app.Fragment
import com.example.proyectoappv3.R
import com.example.proyectoappv3.fragAlumnos.Frag1
import com.example.proyectoappv3.fragAlumnos.Frag2
import com.example.proyectoappv3.fragAlumnos.Frag3
import com.example.proyectoappv3.fragAlumnos.Frag4

class navigation : AppCompatActivity () {

    private lateinit var binding: NavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reemplazaFragment(Frag1())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    reemplazaFragment(Frag1())
                    true
                }
                R.id.cuenta -> {
                    reemplazaFragment(Frag2())
                    true
                }
                R.id.ubicacion -> {
                    reemplazaFragment(Frag3())
                    true
                }
                R.id.notificaciones -> {
                    reemplazaFragment(Frag4())
                    true
                }
                else -> false
            }
        }
    }
    private fun reemplazaFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}