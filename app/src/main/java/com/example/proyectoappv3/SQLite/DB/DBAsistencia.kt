package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.proyectoappv3.SQLite.DB.DBHelper

class DBAsistencia(context: Context) : DBHelper(context) {
    private val db: SQLiteDatabase = readableDatabase

    fun obtenerCantidadAsistencias(nombreUsuario: String, nombreCurso: String): Int {
        var cantidadAsistencias = 0

        val usuarioId = obtenerUsuarioIdPorNombre(nombreUsuario)
        val cursoId = obtenerCursoIdPorNombre(nombreCurso)

        if (usuarioId == null) {
            Log.e("DBAsistencia", "Usuario no encontrado: $nombreUsuario")
            return cantidadAsistencias // Se devuelve 0 si el usuario no se encuentra
        }
        if (cursoId == null) {
            Log.e("DBAsistencia", "Curso no encontrado: $nombreCurso")
            return cantidadAsistencias // Se devuelve 0 si el curso no se encuentra
        }

        try {
            val cursor = db.rawQuery(
                "SELECT COUNT(*) FROM $TABLE_ASISTENCIAS " +
                        "WHERE usuario_id = ? AND curso_id = ?",
                arrayOf(usuarioId.toString(), cursoId.toString())
            )

            if (cursor.moveToFirst()) {
                cantidadAsistencias = cursor.getInt(0) // Obtener el resultado del conteo
            }
            cursor.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return cantidadAsistencias
    }


    private fun obtenerUsuarioIdPorNombre(nombreUsuario: String): Int? {
        var usuarioId: Int? = null

        val cursor = db.rawQuery(
            "SELECT id FROM $TABLE_ALUMNOS WHERE nombre = ?",
            arrayOf(nombreUsuario)
        )

        if (cursor.moveToFirst()) {
            usuarioId = cursor.getInt(0) // Obtener el ID del usuario
        }
        cursor.close()

        return usuarioId
    }

    // Función para obtener el ID del curso a partir del nombre
    private fun obtenerCursoIdPorNombre(nombreCurso: String): Int? {
        var cursoId: Int? = null

        val cursor = db.rawQuery(
            "SELECT id FROM $TABLE_CURSOS WHERE nombre_curso = ?",
            arrayOf(nombreCurso)
        )

        if (cursor.moveToFirst()) {
            cursoId = cursor.getInt(0) // Obtener el ID del curso
        }
        cursor.close()

        return cursoId
    }

}
