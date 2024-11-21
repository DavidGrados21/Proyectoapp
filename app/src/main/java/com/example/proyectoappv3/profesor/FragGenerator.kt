package com.example.proyectoappv3.com.example.proyectoappv3.profesor

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoappv3.QRViewModel
import com.example.proyectoappv3.R
import com.example.proyectoappv3.UserSession
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class FragGenerator : Fragment() {

    private lateinit var txtcodigo: EditText
    private lateinit var txttiempo: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var btnGenerar: Button
    private lateinit var qrViewModel: QRViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_generator, container, false)
        var code = ""

        // Inicializar vistas
        txtcodigo = view.findViewById(R.id.txtcodigo)
        txttiempo = view.findViewById(R.id.txttiempo)
        checkBox = view.findViewById(R.id.checkBox)
        btnGenerar = view.findViewById(R.id.button)

        // Inicializar ViewModel
        qrViewModel = ViewModelProvider(requireActivity())[QRViewModel::class.java]


        btnGenerar.setOnClickListener {

            if (checkBox.isChecked)
            {
                val numeroAleatorio = generarNumeroAleatorio()
                code = numeroAleatorio.toString()
            }
            else
            {
                code = txtcodigo.text.toString()
            }
            val curso = UserSession.currentCourse
            val id = curso?.id
            val fechaActual = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

            val textToEncode = "{\"idcurso\": $id,\"codigo\": $code,\"fecha_vencimiento\": \"$fechaActual\"}"

            try {
                val codeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = codeEncoder.encodeBitmap(textToEncode, BarcodeFormat.QR_CODE, 750, 750)

                // Guardar el bitmap en el ViewModel
                qrViewModel.qrBitmap.value = bitmap
                Toast.makeText(requireContext(), "Código QR generado.", Toast.LENGTH_SHORT).show()

                // Navegar a FragQR
                val fragMostrar = FragQR()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerD, fragMostrar)
                    .addToBackStack(null)
                    .commit()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error al generar el código QR: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    fun generarNumeroAleatorio(): Int {
        return Random.nextInt(100000, 1000000)
    }
}
