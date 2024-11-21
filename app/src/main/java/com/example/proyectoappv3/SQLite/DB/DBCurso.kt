package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.proyectoappv3.SQLite.DB.Entidades.Curso


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

    fun obtenerDatosCurso(nombreCurso: String): Curso? {
        var curso: Curso? = null

        // Usa un bloque try con recursos para manejar la base de datos
        readableDatabase.use { db ->
            val query = "SELECT * FROM $TABLE_CURSOS WHERE nombre_curso = ?"
            db.rawQuery(query, arrayOf(nombreCurso)).use { cursor ->
                if (cursor.moveToFirst()) {
                    try {
                        // Obtiene los datos de la consulta
                        val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                        val nombreCurso = cursor.getString(cursor.getColumnIndexOrThrow("nombre_curso"))
                        val profesorId = cursor.getInt(cursor.getColumnIndexOrThrow("profesor_id"))
                        val salon = cursor.getString(cursor.getColumnIndexOrThrow("salon"))

                        // Crea un objeto Curso con los datos obtenidos
                        curso = Curso(id, nombreCurso , profesorId, salon)
                    } catch (e: IllegalArgumentException) {
                        Log.e("DBAlumnos", "Una o más columnas no existen en la consulta: ${e.message}")
                    }
                }
            }
        }

        return curso
    }
}
