package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DBAsistencia(context: Context) : DBHelper(context) {

    private val db: SQLiteDatabase = readableDatabase

    fun validarYRegistrarAsistencia (idCurso: Int, codigoQR: Int, fechaQR: String, userId: Int) {
        // Obtener la fecha y hora actuales
        val currentDateTime = getCurrentDateTime()

        // Verificar si el código ya fue usado (Condición 1)
        val cursor = db.rawQuery(
            """
            SELECT * FROM $TABLE_ASISTENCIAS 
            WHERE codigo = ? 
            AND curso_id = ?
        """, arrayOf(codigoQR.toString(), idCurso.toString())
        )

        if (cursor.moveToFirst()) {
            // El código QR ya fue usado
        } else {
            // Validar la fecha de vencimiento (Condición 2)
            val cursorVencimiento = db.rawQuery(
                """
                    SELECT fecha_vencimiento FROM $TABLE_ASISTENCIAS
                WHERE curso_id = ?
                AND codigo = ?
            """, arrayOf(idCurso.toString(), codigoQR.toString())

            )

            if (cursorVencimiento.moveToFirst()) {
                val fechaVencimientoIndex = cursorVencimiento.getColumnIndex("fecha_vencimiento")

                if (fechaVencimientoIndex != -1) {  // Verificar que el índice es válido
                    val fechaVencimiento = cursorVencimiento.getString(fechaVencimientoIndex)

                    if (isFechaValida(fechaVencimiento, currentDateTime)) {
                        // Registrar la asistencia si la fecha es válida
                        db.execSQL(
                            """
                INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha)
                VALUES (?, ?, ?, ?)
                """, arrayOf(userId, idCurso, codigoQR, currentDateTime)
                        )
                        // Registro exitoso de asistencia
                    } else {
                        // El código QR ha caducado
                    }
                } else {
                    // Error: columna de fecha de vencimiento no encontrada en la consulta
                }
            } else {
                // El código QR no es válido o no existe en la base de datos
            }
            cursorVencimiento.close()

        }
        cursor.close()
    }

    private fun getCurrentDateTime(): String {
        // Obtener la fecha y hora actuales en formato 'yyyy-MM-dd HH:mm:ss'
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun isFechaValida(fechaVencimiento: String, currentDateTime: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = dateFormat.parse(currentDateTime)
        val vencimientoDate = dateFormat.parse(fechaVencimiento)

        return currentDate?.before(vencimientoDate) == true || currentDate == vencimientoDate
    }
}
