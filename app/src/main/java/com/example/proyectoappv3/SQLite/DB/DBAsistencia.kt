package com.example.proyectoappv3.SQLite.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DBAsistencia(context: Context) : DBHelper(context) {

    private val db: SQLiteDatabase = readableDatabase

    fun codigoUsado(codigo: Int, usuarioId: Int ): Boolean {
        val query = "SELECT 1 FROM $TABLE_ASISTENCIAS WHERE codigo = ? AND usuario_id = ? LIMIT 1"
        val cursor= db.rawQuery(query, arrayOf(codigo.toString(), usuarioId.toString()))

        val existe = cursor.moveToFirst()
        cursor.close()

        return existe
    }

    fun agregarAsistencia(
        usuarioId: Int,
        cursoId: Int,
        codigo: Int,
        fecha: String,
    ): Long {

        var id: Long = 0

        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("usuario_id", usuarioId)
                put("curso_id", cursoId)
                put("codigo", codigo)
                put("fecha", fecha)
            }

            id = db.insert(TABLE_ASISTENCIAS, null, values)
        }
        return id
    }



    fun getCantidadAsistenciasAlumnoCurso(alumnoId: Int, cursoId: Int): Int {
        var cantidadAsistencias = 0

        // Consulta SQL para contar las asistencias de un alumno en un curso específico
        val query = """
        SELECT COUNT(*) as cantidad
        FROM $TABLE_ASISTENCIAS
        WHERE usuario_id = ? AND curso_id = ?
        """.trimIndent()

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

}