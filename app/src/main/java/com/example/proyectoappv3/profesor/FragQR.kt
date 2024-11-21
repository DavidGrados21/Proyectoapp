package com.example.proyectoappv3.com.example.proyectoappv3.profesor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoappv3.QRViewModel
import com.example.proyectoappv3.R

class FragQR : Fragment() {

    private lateinit var qrViewModel: QRViewModel
    private lateinit var imageCodigoqr: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_q_r, container, false)

        imageCodigoqr = view.findViewById(R.id.ImageQR)

        qrViewModel = ViewModelProvider(requireActivity()).get(QRViewModel::class.java)

        qrViewModel.qrBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            bitmap?.let {
                imageCodigoqr.setImageBitmap(it)
            }
        })

        return view
    }

}