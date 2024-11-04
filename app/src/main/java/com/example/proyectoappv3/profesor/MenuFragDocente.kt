package com.example.proyectoappv3.profesor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectoappv3.R
import com.example.proyectoappv3.fragDocente.Frag1D
import com.example.proyectoappv3.fragDocente.Frag2D
import com.example.proyectoappv3.databinding.MenuFragDocenteBinding


class MenuFragDocente : AppCompatActivity() {
    private lateinit var binding: MenuFragDocenteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuFragDocenteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reemplazaFragment(Frag1D())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    reemplazaFragment(Frag1D())
                    true
                }
                R.id.cuenta -> {
                    reemplazaFragment(Frag2D())
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