package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION)
{

    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NOMBRE = "t2.db"

        const val TABLE_ALUMNOS = "alumnos"
        const val TABLE_PROFESORES = "profesores"
        const val TABLE_CURSOS = "cursos"
        const val TABLE_INSCRIPCIONES = "inscripciones"
        const val TABLE_ASISTENCIAS = "asistencias"
        const val TABLE_HORARIOS = "horarios"

        // Columnas de la tabla alumnos
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_NAME = "nombre"
        private const val COLUMN_EMAIL = "correo"
        private const val COLUMN_PASSWORD = "clave"
        private const val COLUMN_PHONE = "telefono"
        private const val COLUMN_PHOTO = "foto"
        private const val COLUMN_CARRERA = "carrera"
        private const val COLUMN_CICLO = "ciclo"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"

        // Columnas de la tabla profesores
        private const val COLUMN_PROF_ID = "id"
        private const val COLUMN_PROF_NAME = "nombre"
        private const val COLUMN_PROF_EMAIL = "correo"
        private const val COLUMN_PROF_PASSWORD = "clave"
        private const val COLUMN_PROF_BIRTHDATE = "fecha_nacimiento"
        private const val COLUMN_PROF_PHONE = "telefono"
        private const val COLUMN_PROF_PHOTO = "foto"

        // Columnas de la tabla cursos
        private const val COLUMN_COURSE_ID = "id"
        private const val COLUMN_COURSE_NAME = "nombre_curso"
        private const val COLUMN_COURSE_PROF_ID = "profesor_id"
        private const val COLUMN_COURSE_CLASSROOM = "salon"

        // Columnas de la tabla inscripciones
        private const val COLUMN_INSCRIPCION_ID = "id"
        private const val COLUMN_INSCRIPCION_USUARIO_ID = "usuario_id"
        private const val COLUMN_INSCRIPCION_CURSO_ID = "curso_id"


        // Columnas de la tabla asistencias
        private const val COLUMN_ASISTENCIA_ID = "id"
        private const val COLUMN_ASISTENCIA_USUARIO_ID = "usuario_id"
        private const val COLUMN_ASISTENCIA_CURSO_ID = "curso_id"
        private const val COLUMN_ASISTENCIA_CODIGO = "codigo"
        private const val COLUMN_ASISTENCIA_FECHA = "fecha"
        private const val COLUMN_ASISTENCIA_FECHA_VENCIMIENTO = "fecha_vencimiento"

        // Columnas de la tabla horarios
        private const val COLUMN_HORARIO_ID = "id"
        private const val COLUMN_HORARIO_CURSO_ID = "curso_id"
        private const val COLUMN_HORARIO_DIA = "dia"
        private const val COLUMN_HORARIO_HORAINICIO = "hora_inicio"

    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        // Crear tabla alumnos (ya existente)
        val createAlumnosTable = ("CREATE TABLE $TABLE_ALUMNOS ("
                + "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_NAME TEXT NOT NULL,"
                + "$COLUMN_EMAIL TEXT NOT NULL,"
                + "$COLUMN_PASSWORD TEXT NOT NULL,"
                + "$COLUMN_PHONE TEXT,"
                + "$COLUMN_PHOTO TEXT,"
                + "$COLUMN_CARRERA TEXT,"
                + "$COLUMN_CICLO TEXT,"
                + "$COLUMN_FECHA_NACIMIENTO TEXT)")
        sqLiteDatabase.execSQL(createAlumnosTable)

        // Crear tabla profesores
        val createProfesoresTable = ("CREATE TABLE $TABLE_PROFESORES ("
                + "$COLUMN_PROF_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_PROF_NAME TEXT NOT NULL,"
                + "$COLUMN_PROF_EMAIL TEXT NOT NULL,"
                + "$COLUMN_PROF_PASSWORD TEXT NOT NULL,"
                + "$COLUMN_PROF_BIRTHDATE TEXT NOT NULL,"
                + "$COLUMN_PROF_PHONE TEXT,"
                + "$COLUMN_PROF_PHOTO TEXT)")
        sqLiteDatabase.execSQL(createProfesoresTable)

        // Crear tabla cursos
        val createCursosTable = ("CREATE TABLE $TABLE_CURSOS ("
                + "$COLUMN_COURSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_COURSE_NAME TEXT NOT NULL,"
                + "$COLUMN_COURSE_PROF_ID INTEGER NOT NULL,"
                + "$COLUMN_COURSE_CLASSROOM TEXT,"
                + "FOREIGN KEY ($COLUMN_COURSE_PROF_ID) REFERENCES $TABLE_PROFESORES($COLUMN_PROF_ID))")
        sqLiteDatabase.execSQL(createCursosTable)

        // Crear tabla inscripciones
        val createInscripcionesTable = ("CREATE TABLE $TABLE_INSCRIPCIONES ("
                + "$COLUMN_INSCRIPCION_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_INSCRIPCION_USUARIO_ID INTEGER NOT NULL,"
                + "$COLUMN_INSCRIPCION_CURSO_ID INTEGER NOT NULL,"
                + "FOREIGN KEY ($COLUMN_INSCRIPCION_USUARIO_ID) REFERENCES $TABLE_ALUMNOS($COLUMN_USER_ID),"
                + "FOREIGN KEY ($COLUMN_INSCRIPCION_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID))")
        sqLiteDatabase.execSQL(createInscripcionesTable)


        val createAttendanceTable = ("CREATE TABLE $TABLE_ASISTENCIAS ("
                + "$COLUMN_ASISTENCIA_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_ASISTENCIA_USUARIO_ID INTEGER NOT NULL,"  // Identificador del usuario (alumno)
                + "$COLUMN_ASISTENCIA_CURSO_ID INTEGER NOT NULL,"  // Identificador del curso
                + "$COLUMN_ASISTENCIA_CODIGO INTEGER NOT NULL,"  // Código QR escaneado
                + "$COLUMN_ASISTENCIA_FECHA TEXT NOT NULL,"  // Fecha y hora del escaneo
                + "$COLUMN_ASISTENCIA_FECHA_VENCIMIENTO TEXT NOT NULL,"  // Fecha de vencimiento del código QR
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_USUARIO_ID) REFERENCES $TABLE_ALUMNOS($COLUMN_USER_ID),"
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID),"
                + "UNIQUE ($COLUMN_ASISTENCIA_USUARIO_ID, $COLUMN_ASISTENCIA_CODIGO))")  // Evita que el mismo usuario registre asistencia con el mismo código
        sqLiteDatabase.execSQL(createAttendanceTable)


        // Crear tabla horarios
        val createHorariosTable = ("CREATE TABLE $TABLE_HORARIOS ("
                + "$COLUMN_HORARIO_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_HORARIO_CURSO_ID INTEGER NOT NULL,"
                + "$COLUMN_HORARIO_DIA TEXT NOT NULL,"
                + "$COLUMN_HORARIO_HORAINICIO TEXT NOT NULL,"
                + "FOREIGN KEY ($COLUMN_HORARIO_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID))")
        sqLiteDatabase.execSQL(createHorariosTable)

        // Insertar registros iniciales en alumnos
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Katherine Lisbeth', 'n00287214@upn.pe', 'clave10321', '955170293', 'https://media.discordapp.net/attachments/1046432412848115753/1304072517828476938/foto_kat.jpg?ex=672e0fac&is=672cbe2c&hm=5fe0dac0fb247592181f24e6924f958961871360f1d21e64f6502149718dbdec&=&format=webp&width=480&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '2001-03-10')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Laura Thalia', 'n00269668@upn.pe', 'clave191221', '930942464', 'https://media.discordapp.net/attachments/1046432412848115753/1304072518088265818/foto_lau.jpg?ex=672e0fac&is=672cbe2c&hm=07393aed2e4755f8f6085628123649fb9135a3453af7d105615d81352c19cce5&=&format=webp&width=473&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '2001-12-19')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Yeimy Paola', 'n00297845@upn.pe', 'clave250602', '978180909', 'https://media.discordapp.net/attachments/1046432412848115753/1304072518088265818/foto_lau.jpg?ex=672e0fac&is=672cbe2c&hm=07393aed2e4755f8f6085628123649fb9135a3453af7d105615d81352c19cce5&=&format=webp&width=473&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2002-06-25')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Carlos Cesar', 'n00273106@upn.pe', 'clave290204', '969754586', 'https://media.discordapp.net/attachments/1046432412848115753/1304072518709149726/foto_carlos.jpg?ex=672e0fac&is=672cbe2c&hm=c87209afd1b8490fc14af36c92ee551c0182ccdc1bc7cfa8773a743b68f7160c&=&format=webp&width=480&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2004-02-29')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Cleiner Nilson', 'n00207290@upn.pe', 'clave290204', '970276517', 'https://media.discordapp.net/attachments/1046432412848115753/1304072519044567060/foto_cleines.jpg?ex=672e0fad&is=672cbe2d&hm=595c319f16a5a9ef05df50e143edc190a46df40e3e6c63da39fdfb29233aeea2&=&format=webp&width=478&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2001-08-30')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Angel Manuel', 'n00319747@upn.pe', 'clave170699', '915947314', 'https://media.discordapp.net/attachments/1046432412848115753/1304072519313133660/foto_angel.jpg?ex=672e0fad&is=672cbe2d&hm=8dc9dcf0d8a4ed7bac56f49b160e353d916ec88be0eee2358d5315a9c58c7407&=&format=webp&width=640&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '1999-06-17')");


        // Insertar registros iniciales en profesores
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_PROFESORES (nombre, correo, clave, fecha_nacimiento, foto, telefono) VALUES ('ROLANDO JAVIER', 'rolando.berru@upn.edu.pe', 'prof1990', '1990-11-24', '', '')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_PROFESORES (nombre, correo, clave, fecha_nacimiento, foto, telefono) VALUES ('MITCHELL PAULO', 'mitchell.blancas@upn.pe', 'prof1989', '1989-01-26', '', '')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_PROFESORES (nombre, correo, clave, fecha_nacimiento, foto, telefono) VALUES ('Jorge Ricardo', 'Jorge.Ricardo@upn.pe', 'prof1973', '1973-01-25', '', '')")

        // Insertar registros iniciales en cursos
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CURSOS (nombre_curso, profesor_id, salon) VALUES ('DESARROLLO DE APLICACIONES MÓVILES 9094', 2, 'LRED1')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CURSOS (nombre_curso, profesor_id, salon) VALUES ('DESARROLLO DE APLICACIONES MÓVILES 9093', 2, 'LCOM6')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CURSOS (nombre_curso, profesor_id, salon) VALUES ('REDES 2 9079', 1, 'LRED1')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CURSOS (nombre_curso, profesor_id, salon) VALUES ('REDES 2 9078', 1, 'LRED1')")

        // Insertar registros iniciales en inscripciones
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (1, 1);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (2, 1);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (3, 1);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (4, 2);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (5, 2);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (6, 2);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (1, 3);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (2, 3);")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_INSCRIPCIONES (usuario_id, curso_id) VALUES (4, 4);")

        // Insertar registros iniciales en horarios
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_HORARIOS (curso_id, dia, hora_inicio) VALUES (1, 'Martes', '17:50:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_HORARIOS (curso_id, dia, hora_inicio) VALUES (2, 'Jueves', '14:30:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_HORARIOS (curso_id, dia, hora_inicio) VALUES (3, 'Lunes', '09:10:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_HORARIOS (curso_id, dia, hora_inicio) VALUES (4, 'Lunes', '14:30:00');")

        // Insertar registros iniciales en asistencias
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha, fecha_vencimiento) VALUES (1, 1, 1, '2023-11-22 17:55:00', '2023-11-22 18:00:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha, fecha_vencimiento) VALUES (1, 1, 2, '2023-11-29 18:00:00', '2023-11-29 18:00:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha, fecha_vencimiento) VALUES (1, 3, 3, '2023-11-21 09:13:00', '2023-11-21 09:20:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha, fecha_vencimiento) VALUES (1, 3, 4, '2023-11-28 09:15:00', '2023-11-28 09:20:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha, fecha_vencimiento) VALUES (1, 3, 5, '2023-11-14 09:10:00', '2023-11-14 09:20:00');")

    }


    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_PROFESORES")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_CURSOS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_INSCRIPCIONES")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_ASISTENCIAS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_HORARIOS")

        onCreate(sqLiteDatabase)
    }
}
