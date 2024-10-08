package com.example.proyectoappv3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.databinding.NavigationBinding
import androidx.fragment.app.Fragment

class navigation : AppCompatActivity () {

    private lateinit var binding: NavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

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