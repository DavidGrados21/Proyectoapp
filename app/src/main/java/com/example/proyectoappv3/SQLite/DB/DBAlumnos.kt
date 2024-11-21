package com.example.proyectoappv3.SQLite.DB

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.proyectoappv3.SQLite.DB.Entidades.Alumno

class DBAlumnos(context: Context) : DBHelper(context) {

    fun insertAlumno(
        nombre: String,
        password: String,
        correo: String,
        carrera: String,
        ciclo: String,
        fechaNacimiento: String,
    ): Long {
        var id: Long = 0

        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("nombre", nombre)
                put("correo", correo)
                put("clave", password)
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

    fun getAlumnoIdByName(nombre: String): Int? {
        var userId: Int? = null
        readableDatabase.use { db ->
            val query = "SELECT id FROM $TABLE_ALUMNOS WHERE nombre = ?"
            db.rawQuery(query, arrayOf(nombre)).use { cursor ->

                if (cursor.moveToFirst()) {
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                }
                cursor.close()
                return userId
            }
        }
    }


    fun actualizarAlumno(
        id: Int?,
        nombre: String,
        password: String,
        correo: String,
        carrera: String,
        ciclo: String,
        fechaNacimiento: String,
        foto: String?
    ): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("nombre", nombre)
            put("correo", correo)
            put("clave", password)
            put("carrera", carrera)
            put("ciclo", ciclo)
            put("fecha_nacimiento", fechaNacimiento)
            put("foto", foto)
        }
        return db.update(
            TABLE_ALUMNOS,
            contentValues,
            "id = ?",
            arrayOf(id.toString())
        ).also {
            db.close()
        }
    }

    fun obtenerNombresDeAlumnos(cursoId: Int): List<String> {
        val nombres = mutableListOf<String>()

        // Definir la consulta SQL para obtener solo los nombres de los estudiantes en un curso
        val query = """
        SELECT a.nombre
        FROM $TABLE_ALUMNOS a
        JOIN $TABLE_INSCRIPCIONES i ON a.id = i.usuario_id
        WHERE i.curso_id = ?
    """

        val db = readableDatabase
        val cursor = db.rawQuery(query, arrayOf(cursoId.toString()))

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex("nombre")
            if (columnIndex != -1) {
                do {
                    val nombre = cursor.getString(columnIndex)
                    nombres.add(nombre)
                } while (cursor.moveToNext())
            } else {
                Log.e("SQLite", "La columna 'nombre' no se encuentra en la consulta.")
            }
        } else {
            Log.e("SQLite", "No se encontraron resultados en la consulta.")
        }

        cursor.close()
        db.close()

        return nombres
    }
}
