package com.example.proyectoappv3.SQLite.DB

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.proyectoappv3.SQLite.DB.Entidades.Alumno

class DBAlumnos(context: Context) : DBHelper(context) {
    private val context: Context = context

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
                put("clave", password) // Asegúrate de que la clave sea la columna correcta
                put("carrera", carrera)
                put("ciclo", ciclo)
                put("fecha_nacimiento", fechaNacimiento)
            }

            id = db.insert(TABLE_ALUMNOS, null, values)
        }

        return id
    }

    fun verificarLogin(nombre: String, password: String): Boolean {
        var isValid = false

        return try {
            readableDatabase.use { db ->
                db.rawQuery(
                    "SELECT * FROM $TABLE_ALUMNOS WHERE correo = ? AND clave = ?",
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

    fun obtenerDatosUsuario(correo: String): Alumno? {
        var alumno: Alumno? = null

        // Usa un bloque try con recursos para manejar la base de datos
        readableDatabase.use { db ->
            val query = "SELECT * FROM $TABLE_ALUMNOS WHERE correo = ?"
            db.rawQuery(query, arrayOf(correo)).use { cursor ->
                if (cursor.moveToFirst()) {
                    try {
                        // Obtiene el id como Int
                        val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                        val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                        val password = cursor.getString(cursor.getColumnIndexOrThrow("clave"))
                        val telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
                        val foto = cursor.getString(cursor.getColumnIndexOrThrow("foto"))
                        val carrera = cursor.getString(cursor.getColumnIndexOrThrow("carrera"))
                        val ciclo = cursor.getString(cursor.getColumnIndexOrThrow("ciclo"))
                        val fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_nacimiento"))

                        // Crea un objeto Alumno con los datos obtenidos
                        alumno = Alumno(id, nombre, correo, password, telefono, foto, carrera, ciclo, fechaNacimiento)
                    } catch (e: IllegalArgumentException) {
                        Log.e("DBAlumnos", "Una o más columnas no existen en la consulta: ${e.message}")
                    }
                }
            }
        }

        return alumno
    }
}
