package com.example.proyectoappv3.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper (context: Context?):
    SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NOMBRE = "t2.db"
        const val TABLE_ALUMNOS = "alumnos"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_NAME = "nombre"
        private const val COLUMN_LASTNAME = "apellido"
        private const val COLUMN_EMAIL = "correo"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_CARRERA = "carrera"
        private const val COLUMN_CICLO = "ciclo"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val createUserTable = ("CREATE TABLE $TABLE_ALUMNOS ("
                + "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_NAME TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_PASSWORD TEXT,"
                + "$COLUMN_CARRERA TEXT,"
                + "$COLUMN_CICLO TEXT,"
                + "$COLUMN_FECHA_NACIMIENTO TEXT)")
        sqLiteDatabase.execSQL(createUserTable)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        onCreate(sqLiteDatabase)
    }
}

