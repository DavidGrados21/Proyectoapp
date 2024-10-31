package com.example.proyectoappv3.fragAlumnos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.proyectoappv3.Fragverificacion
import com.example.proyectoappv3.R
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView

class FragScaner : Fragment() {

    // Variables para las vistas
    private lateinit var txtCodigoqr: TextView
    private lateinit var barcodeView: BarcodeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragscaner, container, false)

        // Inicialización de las vistas
        txtCodigoqr = view.findViewById(R.id.codigo)
        barcodeView = view.findViewById(R.id.barcode_scanner)

        // Iniciar escaneo de forma automática al abrir el fragmento
        iniciarEscaneo()

        return view
    }

    private fun iniciarEscaneo() {
        // Mostrar la cámara y comenzar el escaneo
        barcodeView.visibility = View.VISIBLE
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                txtCodigoqr.text = "Código QR escaneado: ${result.text}"

                if (result.text == "23345") {
                    cambiarFragmento()
                } else {
                    Toast.makeText(context, "Código no reconocido. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack() // Destruir el fragmento actual
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })

        barcodeView.resume() // Iniciar la cámara
    }

    private fun cambiarFragmento() {
        // Crear una instancia del nuevo fragmento
        val fragVerificacion = Fragverificacion()

        // Obtener el fragment manager y realizar la transacción
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragVerificacion) // Asegúrate de que el ID sea correcto
        transaction.addToBackStack(null) // Permitir volver al fragmento anterior
        transaction.commit()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause() // Pausar la cámara cuando el fragmento se pausa
    }

    override fun onResume() {
        super.onResume()
        // Verificar si el BarcodeView está visible antes de reanudar
        if (barcodeView.visibility == View.VISIBLE) {
            barcodeView.resume() // Reanudar la cámara
        }
    }
}
