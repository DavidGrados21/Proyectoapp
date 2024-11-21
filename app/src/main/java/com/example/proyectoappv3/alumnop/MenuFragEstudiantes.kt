package com.example.proyectoappv3.alumnop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoappv3.databinding.MenuFragEstudiantesBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.proyectoappv3.R
import com.example.proyectoappv3.fragAlumnos.Frag1
import com.example.proyectoappv3.fragAlumnos.Frag2
import com.example.proyectoappv3.fragAlumnos.Frag3
import com.example.proyectoappv3.fragAlumnos.Frag4

class MenuFragEstudiantes : AppCompatActivity() {

    private lateinit var binding: MenuFragEstudiantesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MenuFragEstudiantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adaptador para el ViewPager2
        val adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> Frag1()
                    1 -> Frag2()
                    2 -> Frag3()
                    3 -> Frag4()
                    else -> Frag1()
                }
            }

            override fun getItemCount(): Int = 4
        }

        val viewPager = binding.viewPagerE
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> viewPager.setCurrentItem(0, true)
                R.id.cuenta -> viewPager.setCurrentItem(1, true)
                R.id.ubicacion -> viewPager.setCurrentItem(2, true)
                R.id.notificaciones -> viewPager.setCurrentItem(3, true)
                else -> {
                    viewPager.setCurrentItem(0, true)
                }
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

    }

}