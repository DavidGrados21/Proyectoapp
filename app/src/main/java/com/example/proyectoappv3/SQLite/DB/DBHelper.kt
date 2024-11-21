package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION)
{

    companion object {
        private const val DATABASE_VERSION = 15
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


        // Crear tabla asistencias
        val createAttendanceTable = ("CREATE TABLE $TABLE_ASISTENCIAS ("
                + "$COLUMN_ASISTENCIA_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_ASISTENCIA_USUARIO_ID INTEGER NOT NULL,"
                + "$COLUMN_ASISTENCIA_CURSO_ID INTEGER NOT NULL,"
                + "$COLUMN_ASISTENCIA_CODIGO INTEGER NOT NULL,"
                + "$COLUMN_ASISTENCIA_FECHA TEXT NOT NULL,"
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_USUARIO_ID) REFERENCES $TABLE_ALUMNOS($COLUMN_USER_ID),"
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID),"
                + "UNIQUE ($COLUMN_ASISTENCIA_USUARIO_ID, $COLUMN_ASISTENCIA_CODIGO))")
        sqLiteDatabase.execSQL(createAttendanceTable)


        // Crear tabla horarios
        val createHorariosTable = ("CREATE TABLE $TABLE_HORARIOS ("
                + "$COLUMN_HORARIO_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_HORARIO_CURSO_ID INTEGER NOT NULL,"
                + "$COLUMN_HORARIO_DIA TEXT NOT NULL,"
                + "$COLUMN_HORARIO_HORAINICIO TEXT NOT NULL,"
                + "FOREIGN KEY ($COLUMN_HORARIO_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID))")
        sqLiteDatabase.execSQL(createHorariosTable)

        insertInitialData(sqLiteDatabase)
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

    private fun insertInitialData(sqLiteDatabase: SQLiteDatabase) {
        // Inserciones iniciales en la tabla alumnos
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Katherine Lisbeth', 'n00287214@upn.pe', 'clave10321', '955170293', 'https://media.discordapp.net/attachments/1046432412848115753/1304072517828476938/foto_kat.jpg?ex=673de1ac&is=673c902c&hm=6bb7f3b5a0f783623a27cceaa0fb7c00b04b2f0fddc97895e13d1fe5dcf6a45f&=&format=webp&width=480&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '2001-03-10')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Laura Thalia', 'n00269668@upn.pe', 'clave191221', '930942464', 'https://media.discordapp.net/attachments/1046432412848115753/1304072518088265818/foto_lau.jpg?ex=673de1ac&is=673c902c&hm=d6d672e19e01e78e5eea9059d0c4be5569e667df4785bfbed67b1b134a1ad7cb&=&format=webp&width=473&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '2001-12-19')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Yeimy Paola', 'n00297845@upn.pe', 'clave250602', '978180909', 'https://media.discordapp.net/attachments/1046432412848115753/1304072518344249414/foto_pao.jpg?ex=673de1ac&is=673c902c&hm=57e2b353bb7ed8ead579fac2cb67275b7ea275a5ec82cae459197992f2431211&=&format=webp&width=476&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2002-06-25')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Carlos Cesar', 'n00273106@upn.pe', 'clave290204', '969754586', 'https://media.discordapp.net/attachments/1046432412848115753/1304072518709149726/foto_carlos.jpg?ex=673de1ac&is=673c902c&hm=0b0546893275da4d48d3329d7252ec892b5c6cf483622f8ded8e38644d3823a0&=&format=webp&width=480&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2004-02-29')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Cleiner Nilson', 'n00207290@upn.pe', 'clave290204', '970276517', 'https://media.discordapp.net/attachments/1046432412848115753/1304072519044567060/foto_cleines.jpg?ex=673de1ad&is=673c902d&hm=971fda7853b9e71ccbe7cad67278d83553e1289a474cdf1c5c386b4a1393c964&=&format=webp&width=478&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2001-08-30')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Angel Manuel', 'n00319747@upn.pe', 'clave170699', '915947314', 'https://media.discordapp.net/attachments/1046432412848115753/1304072519313133660/foto_angel.jpg?ex=673de1ad&is=673c902d&hm=5e1a0f488228b23bf7295b2976404adecd72632f4365ff632416dce0f2a64ceb&=&format=webp&width=640&height=480', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '1999-06-17')")

        // Inserciones iniciales en la tabla profesores
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_PROFESORES (nombre, correo, clave, fecha_nacimiento, foto, telefono) VALUES ('ROLANDO JAVIER', 'rolando.berru@upn.edu.pe', 'prof1990', '1990-11-24', 'https://media.discordapp.net/attachments/1046432412848115753/1308595453427978240/profRolando.jpeg?ex=673e83fc&is=673d327c&hm=7e3f46472f726ee6375fbebca80a135cba02ca4404bcffe574567deb1e3dec24&=&format=webp', '')")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_PROFESORES (nombre, correo, clave, fecha_nacimiento, foto, telefono) VALUES ('MITCHELL PAULO', 'mitchell.blancas@upn.pe', 'prof1989', '1989-01-26', 'https://media.discordapp.net/attachments/1046432412848115753/1308595453146824755/profMitchell.jpg?ex=673e83fc&is=673d327c&hm=e0cb18c439a518ce7dd088ad2aa77d950a5d119e5eafa0cce971af06d1bbe3b3&=&format=webp&width=480&height=480', '')")

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
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 1, 1, '2023-11-22 17:55:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 1, 2, '2023-11-29 18:00:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 3, 3, '2023-11-21 09:13:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 3, 4, '2023-11-28 09:15:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 3, 5, '2023-11-14 09:10:00');")

    }
}
