package com.example.proyectoappv3.profesor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.proyectoappv3.R
import com.example.proyectoappv3.databinding.MenuFragDocenteBinding
import com.example.proyectoappv3.fragDocente.Frag1D
import com.example.proyectoappv3.fragDocente.Frag2D

class MenuFragDocente : AppCompatActivity() {

    private lateinit var binding: MenuFragDocenteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuFragDocenteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar adaptador del ViewPager2
        val adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> Frag1D()
                    1 -> Frag2D()
                    else -> throw IllegalArgumentException("Posición no válida")
                }
            }

            override fun getItemCount(): Int = 2
        }

        val viewPager = binding.viewPagerD
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        // Sincronizar ViewPager con BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> viewPager.setCurrentItem(0, true)
                R.id.cuenta -> viewPager.setCurrentItem(1, true)
                else -> false
            }
            true
        }

        // Sincronizar BottomNavigationView con el ViewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })
    }
}
