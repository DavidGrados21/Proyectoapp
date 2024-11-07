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

import com.example.proyectoappv3.SQLite.DB.DBAsistencia
import com.example.proyectoappv3.UserSession
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import org.json.JSONObject

class FragScaner : Fragment() {

    // Variables para las vistas
    private lateinit var dbHelper: DBAsistencia
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


        dbHelper = DBAsistencia(requireContext())


        return view
    }

    private fun iniciarEscaneo() {
        // Mostrar la cámara y comenzar el escaneo
        barcodeView.visibility = View.VISIBLE
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                txtCodigoqr.text = "Código QR escaneado: ${result.text}"

                // Parsear el contenido del QR (en formato JSON)
                val qrContent = result.text
                val qrValues = parseQRCode(qrContent)
                val alumno = UserSession.currentUser


                // Extraer los valores del QR
                val idCurso = qrValues["idcurso"] as? Int
                val codigoQR = qrValues["codigo"] as? Int
                val fechaQR = qrValues["fecha"] as? String
                val userId = alumno?.id

                if (userId != null && idCurso != null && codigoQR != null && fechaQR != null) {
                    // Validar las condiciones antes de registrar la asistencia
                    dbHelper.validarYRegistrarAsistencia(idCurso, codigoQR, fechaQR, userId)
                } else {
                    val mensajeError = when {
                        userId == null -> "Error: usuario no válido."
                        idCurso == null -> "Error: curso no válido."
                        codigoQR == null -> "Error: código QR no válido."
                        fechaQR == null -> "Error: fecha de QR no válida."
                        else -> "Código QR inválido. Intente de nuevo."
                    }
                    Toast.makeText(context, mensajeError, Toast.LENGTH_SHORT).show()
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

    private fun parseQRCode(qrContent: String): Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        try {
            // Convertir el contenido del QR (JSON) a un objeto JSONObject
            val jsonObject = JSONObject(qrContent)

            // Extraer los valores del JSON
            val idCurso = jsonObject.getInt("idcurso")
            val codigo = jsonObject.getInt("codigo")
            val fecha = jsonObject.getString("fecha")

            // Guardar los valores en un mapa
            result["idcurso"] = idCurso
            result["codigo"] = codigo
            result["fecha"] = fecha

        } catch (e: Exception) {
            // En caso de error con el formato JSON
            println("Error al parsear el QR: ${e.message}")
        }

        return result
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
