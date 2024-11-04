package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase


class DBCurso(context: Context) : DBHelper(context) {

    private val db: SQLiteDatabase = readableDatabase

    fun obtenerCursosPorNombreUsuario(nombreUsuario: String): List<String> {
        val cursos = mutableListOf<String>()

        val query = """
            SELECT c.nombre_curso
            FROM cursos AS c
            INNER JOIN inscripciones AS i ON c.id = i.curso_id
            INNER JOIN alumnos AS u ON i.usuario_id = u.id
            WHERE u.nombre = ?
        """

        db.rawQuery(query, arrayOf(nombreUsuario)).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val nombreCurso = cursor.getString(cursor.getColumnIndexOrThrow("nombre_curso"))
                    cursos.add(nombreCurso)
                } while (cursor.moveToNext())
            }
        }

        return cursos
    }

    fun obtenerCursosDeProfesor(profesorId: Int): List<String> {
        val cursos = mutableListOf<String>()
        val query = "SELECT nombre_curso FROM $TABLE_CURSOS WHERE profesor_id = ?"

        db.rawQuery(query, arrayOf(profesorId.toString())).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val nombreCurso = cursor.getString(cursor.getColumnIndexOrThrow("nombre_curso"))
                    cursos.add(nombreCurso)
                } while (cursor.moveToNext())
            }
        }

        return cursos
    }
}
