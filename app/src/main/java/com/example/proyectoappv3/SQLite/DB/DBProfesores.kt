package com.example.proyectoappv3.SQLite.DB

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.proyectoappv3.SQLite.DB.Entidades.Alumno
import com.example.proyectoappv3.SQLite.DB.Entidades.Profesor

class DBProfesores (context: Context) : DBHelper(context) {
    private val context: Context

    init {
        this.context = context
    }
    fun insertProfesor(
        nombre: String,
        password: String,
        correo: String,
        telefono: String,
        fechaNacimiento: String,
    ): Long {
        var id: Long = 0

        // Usa un bloque try con recursos para manejar la base de datos
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("nombre", nombre)
                put("correo", correo)
                put("clave", password)
                put("telefono", telefono)
                put("fecha_nacimiento", fechaNacimiento)
            }

            id = db.insert(TABLE_PROFESORES, null, values)
        }

        return id
    }

    // Método para verificar el login de un profesor
    fun verificarLogin(nombre: String, password: String): Boolean {
        var isValid = false

        return try {
            readableDatabase.use { db ->
                db.rawQuery(
                    "SELECT * FROM $TABLE_PROFESORES WHERE correo = ? AND clave = ?",
                    arrayOf(nombre, password)
                ).use { cursor ->
                    isValid = cursor.moveToFirst()
                }
            }
            isValid
        } catch (e: Exception) {
            false
        }
    }
    fun obtenerDatosProfesor(correo: String): Profesor? {
        var profesor: Profesor? = null

        // Usa un bloque try con recursos para manejar la base de datos
        readableDatabase.use { db ->
            val query = "SELECT * FROM $TABLE_PROFESORES WHERE correo = ?"
            db.rawQuery(query, arrayOf(correo)).use { cursor ->
                if (cursor.moveToFirst()) {
                    try {
                        // Obtiene el id como Int
                        val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                        val password = cursor.getString(cursor.getColumnIndexOrThrow("clave"))
                        val telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
                        val foto = cursor.getString(cursor.getColumnIndexOrThrow("foto"))
                        val fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento"))

                        // Crea un objeto Profesor con los datos obtenidos
                        profesor = Profesor(id, nombre, correo, password, telefono, foto, fechaNacimiento)
                    } catch (e: IllegalArgumentException) {
                        Log.e("DBProfesores", "Una o más columnas no existen en la consulta: ${e.message}")
                    }
                }
            }
        }

        return profesor
    }

}