package com.example.proyectoappv3.com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.icu.text.SimpleDateFormat
import com.example.proyectoappv3.SQLite.DB.DBHelper
import java.util.Locale

class DBHorario (context: Context) : DBHelper(context){

    private val db: SQLiteDatabase = readableDatabase

    fun obtenerHoraInicioCurso(nombreCurso: String): String? {
        val db = this.readableDatabase
        val query = """
        SELECT h.hora_inicio
        FROM horarios AS h
        INNER JOIN cursos ON h.curso_id = cursos.id
        WHERE cursos.nombre_curso  = ?
    """

        val cursor = db.rawQuery(query, arrayOf(nombreCurso))
        var horaInicio: String? = null

        if (cursor.moveToFirst()) {
            // Obtener la hora en formato original de la base de datos
            val horaInicioRaw = cursor.getString(cursor.getColumnIndexOrThrow("hora_inicio"))

            // Convertir de formato "HH:mm" a "hh:mm a.m./p.m."
            val formato24Horas = SimpleDateFormat("HH:mm", Locale.getDefault())
            val formato12Horas = SimpleDateFormat("hh:mm a", Locale.getDefault())

            val fechaHora = formato24Horas.parse(horaInicioRaw) // Parsear la hora en formato 24h
            horaInicio = formato12Horas.format(fechaHora).replace("AM", "a.m.").replace("PM", "p.m.") // Formatear a 12h
        }
        cursor.close()
        db.close()

        return horaInicio
    }

}