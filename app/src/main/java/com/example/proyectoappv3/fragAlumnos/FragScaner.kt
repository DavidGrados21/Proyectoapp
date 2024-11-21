package com.example.proyectoappv3.fragAlumnos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyectoappv3.R
import com.example.proyectoappv3.SQLite.DB.DBAsistencia
import com.example.proyectoappv3.UserSession
import com.example.proyectoappv3.alumnop.MenuFragEstudiantes
import com.example.proyectoappv3.com.example.proyectoappv3.fragAlumnos.Fragverificacion
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import org.json.JSONObject

class FragScaner : Fragment() {

    // Variables para las vistas
    private lateinit var dbHelper: DBAsistencia
    private lateinit var barcodeView: BarcodeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragscaner, container, false)
        barcodeView = view.findViewById(R.id.barcode_scanner)
        iniciarEscaneo()
        dbHelper = DBAsistencia(requireContext())
        return view
    }

    // Función para iniciar el escaneo
    private fun iniciarEscaneo() {
        // Mostrar la cámara y comenzar el escaneo
        barcodeView.visibility = View.VISIBLE
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                val qrContent = result.text
                val qrValues = parseQRCode(qrContent)
                val alumno = UserSession.currentUser

                // Extraer los valores del QR, usando el campo correcto para fecha
                val idCurso = qrValues["idcurso"] as? Int
                val codigoQR = qrValues["codigo"] as? Int
                val fechaQR = qrValues["fecha_vencimiento"] as? String
                val userId = alumno?.id

                // Pausar el escaneo para evitar múltiples lecturas del mismo código
                barcodeView.pause()

                // Validar campos y registrar asistencia
                if (userId != null && idCurso != null && codigoQR != null && fechaQR != null) {
                    if (dbHelper.codigoUsado(codigoQR, userId)) {
                        Toast.makeText(context, "Código QR ya escaneado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MenuFragEstudiantes::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        dbHelper.agregarAsistencia(userId, idCurso, codigoQR, fechaQR)
                        Toast.makeText(context, "Asistencia registrada", Toast.LENGTH_SHORT).show()
                        val fragment = Fragverificacion()
                        val fragmentManager = parentFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()

                        fragmentTransaction.replace(R.id.fragmentContainerE, fragment)
                        fragmentTransaction.commit()
                    }
                }
                else
                {
                    val mensajeError = when {
                        userId == null -> "Error: usuario no válido."
                        idCurso == null -> "Error: curso no válido."
                        codigoQR == null -> "Error: código QR no válido."
                        fechaQR == null -> "Error: fecha de QR no válida."
                        else -> "Código QR inválido. Intente de nuevo."
                    }
                    Toast.makeText(context, mensajeError, Toast.LENGTH_SHORT).show()
                }
                barcodeView.resume()
            }
        })

        barcodeView.resume()
    }


    private fun parseQRCode(content: String): Map<String, Any> {
        val json = JSONObject(content) // Convierte el contenido del QR en un objeto JSON
        return mapOf(
            "idcurso" to json.optInt("idcurso"),
            "codigo" to json.optInt("codigo"),
            "fecha_vencimiento" to json.optString("fecha_vencimiento")
        )
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
