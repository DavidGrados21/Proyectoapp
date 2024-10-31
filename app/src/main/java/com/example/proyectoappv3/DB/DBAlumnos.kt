package com.example.proyectoappv3.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class DBAlumnos (context: Context) : DBHelper(context) {
    private val context: Context

    init {
        this.context = context
    }

    fun insertAlumno(
        nombre: String,
        password: String,
        correo: String,
        carrera: String,
        ciclo: String,
        fechaNacimiento: String,
    ): Long {
        var id: Long = 0

        // Usa un bloque try con recursos para manejar la base de datos
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("nombre", nombre)
                put("correo", correo)
                put("password", password)
                put("carrera", carrera)
                put("ciclo", ciclo)
                put("fecha_nacimiento", fechaNacimiento)
            }

            id = db.insert(TABLE_ALUMNOS, null, values)
        }

        return id
    }

    // Método para verificar el login de un alumno
    fun verificarLogin(nombre: String, password: String): Boolean {
        var isValid = false

        // Usa un bloque try con recursos para manejar la base de datos
        readableDatabase.use { db ->
            db.rawQuery(
                "SELECT * FROM $TABLE_ALUMNOS WHERE correo = ? AND password = ?",
                arrayOf(nombre, password)
            ).use { cursor ->
                isValid = cursor.moveToFirst() // Verifica si se encontró el registro
            }
        }

        return isValid
    }

}