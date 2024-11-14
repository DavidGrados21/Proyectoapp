package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DBAsistencia(context: Context) : DBHelper(context) {

    private val db: SQLiteDatabase = readableDatabase

    fun validarYRegistrarAsistencia (idCurso: Int, codigoQR: Int, fechaQR: String, userId: Int) {
        val currentDateTime = getCurrentDateTime()
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

    fun getCantidadAsistenciasAlumnoCurso(alumnoId: Int, cursoId: Int): Int {
        var cantidadAsistencias = 0

        // Consulta SQL para contar las asistencias de un alumno en un curso específico
        val query = """
        SELECT COUNT(*) as cantidad
        FROM $TABLE_ASISTENCIAS
        WHERE usuario_id = ? AND curso_id = ?
        """.trimIndent()

        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(alumnoId.toString(), cursoId.toString()))

        // Obtener el resultado
        if (cursor.moveToFirst()) {
            cantidadAsistencias = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"))
        }
        cursor.close()

        return cantidadAsistencias
    }

    fun getCursoIdByName(nombreCurso: String): Int? {
        var cursoId: Int? = null

        // Consulta SQL para obtener el id del curso a partir de su nombre
        val query = """
        SELECT id
        FROM $TABLE_CURSOS
        WHERE nombre_curso = ?
    """.trimIndent()

        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(nombreCurso))

        // Verificar si hay un resultado
        if (cursor.moveToFirst()) {
            cursoId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
        }
        cursor.close()

        return cursoId
    }

    fun getAlumnoIdByName(nombreAlumno: String): Int? {
        var alumnoId: Int? = null

        // Consulta SQL para obtener el id del alumno a partir de su nombre
        val query = """
        SELECT id
        FROM $TABLE_ALUMNOS
        WHERE nombre = ?
    """.trimIndent()

        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(nombreAlumno))

        // Verificar si hay un resultado
        if (cursor.moveToFirst()) {
            alumnoId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
        }
        cursor.close()

        return alumnoId
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
