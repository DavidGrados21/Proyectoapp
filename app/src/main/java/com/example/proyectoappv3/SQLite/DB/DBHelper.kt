package com.example.proyectoappv3.SQLite.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION)
{

    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NOMBRE = "t2.db"

        const val TABLE_ALUMNOS = "alumnos"
        const val TABLE_PROFESORES = "profesores"
        const val TABLE_CURSOS = "cursos"
        const val TABLE_INSCRIPCIONES = "inscripciones"
        const val TABLE_CODIGOSQR = "codigos_asistencia"
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

        // Columnas de la tabla codigosQR
        private const val COLUMN_CODIGOQR_ID = "id"
        private const val COLUMN_CODIGOQR_CURSO_ID = "curso_id"
        private const val COLUMN_CODIGOQR_CODIGO = "codigo"
        private const val COLUMN_CODIGOQR_FECHA = "fecha"

        // Columnas de la tabla asistencias
        private const val COLUMN_ASISTENCIA_ID = "id"
        private const val COLUMN_ASISTENCIA_USUARIO_ID = "usuario_id"
        private const val COLUMN_ASISTENCIA_CURSO_ID = "curso_id"
        private const val COLUMN_ASISTENCIA_CODIGO_ID = "codigo"
        private const val COLUMN_ASISTENCIA_FECHA = "fecha"

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

        // Crear tabla codigosQR
        val createCodigosQRTable = ("CREATE TABLE $TABLE_CODIGOSQR ("
                + "$COLUMN_CODIGOQR_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_CODIGOQR_CURSO_ID INTEGER NOT NULL,"
                + "$COLUMN_CODIGOQR_CODIGO TEXT NOT NULL,"
                + "$COLUMN_CODIGOQR_FECHA TEXT NOT NULL,"
                + "FOREIGN KEY ($COLUMN_CODIGOQR_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID))")
        sqLiteDatabase.execSQL(createCodigosQRTable)

        // Crear tabla asistencias
        val createAttendanceTable = ("CREATE TABLE $TABLE_ASISTENCIAS ("
                + "$COLUMN_ASISTENCIA_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_ASISTENCIA_USUARIO_ID INTEGER NOT NULL,"
                + "$COLUMN_ASISTENCIA_CURSO_ID INTEGER NOT NULL,"
                + "$COLUMN_ASISTENCIA_CODIGO_ID INTEGER NOT NULL,"
                + "$COLUMN_ASISTENCIA_FECHA TEXT NOT NULL,"
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_USUARIO_ID) REFERENCES $TABLE_ALUMNOS($COLUMN_USER_ID),"
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_CURSO_ID) REFERENCES $TABLE_CURSOS($COLUMN_COURSE_ID),"
                + "FOREIGN KEY ($COLUMN_ASISTENCIA_CODIGO_ID) REFERENCES $TABLE_CODIGOSQR($COLUMN_CODIGOQR_ID))")
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
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Katherine Lisbeth', 'n00287214@upn.pe', 'clave10321', '955170293', 'https://scontent-lim1-1.xx.fbcdn.net/v/t39.30808-6/347255620_622983216369954_5647461411018028271_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeHenBgNbV3q4MRkSdlBuSSIM3RwZ7XZOtszdHBntdk628iYRhS52VAJCkiUdWbyrOzDYEftt9tP6E_nOoQ3p9uj&_nc_ohc=7dFK9u5AlFcQ7kNvgH251kj&_nc_zt=23&_nc_ht=scontent-lim1-1.xx&_nc_gid=ASMhO8p-ycksGnwBjWrrKNz&oh=00_AYD1-uRuAQIoBz7uydRKjNm2N9OO4eM06ATtD38XyG_lqQ&oe=672B92F3', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '2001-03-10')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Laura Thalia', 'n00269668@upn.pe', 'clave191221', '930942464', 'https://scontent-lim1-1.xx.fbcdn.net/v/t39.30808-6/382189946_3446245889019563_6652190030691144574_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeGSJUSvHny9U-WYyz2CKp-S7dLbdKMHy4nt0tt0owfLiWfZYFA3bwScts4JJPW8SF2LCOFXperBNIVZQku_1R8c&_nc_ohc=fwxg2vTomTcQ7kNvgEiMFW7&_nc_zt=23&_nc_ht=scontent-lim1-1.xx&_nc_gid=A8-mYGOIDcMArCrE0dVMivd&oh=00_AYAALaLuQMKRyHLdiOUMlt05ZOyeaZdT2ind1GuXSg79tA&oe=672B97E2', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '2001-12-19')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Yeimy Paola', 'n00297845@upn.pe', 'clave250602', '978180909', 'https://scontent-lim1-1.xx.fbcdn.net/v/t39.30808-6/423694917_6647244738714685_5074942817859452161_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeFvKQdP3PtA82UUUz_Ftxe1YAT294dhYzhgBPb3h2FjOI4GpdVK6q6cdiHsu9UASTuX85M6agU_3krpcTuNdFCN&_nc_ohc=7qbOMarpAiEQ7kNvgH0qw2e&_nc_zt=23&_nc_ht=scontent-lim1-1.xx&_nc_gid=APFp_lwrZG4fnBoPORLRnCr&oh=00_AYCC7r6YcewIRDlzkAwU8qHz90FbojBEyVAcZLqzXvOLpQ&oe=672B71FD', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2002-06-25')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Carlos Cesar', 'n00273106@upn.pe', 'clave290204', '969754586', 'https://scontent-lim1-1.xx.fbcdn.net/v/t1.6435-9/57602646_1646493845494301_9098441664531267584_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeHLuSHCgRiXFCvkFEo3bjL8sjpTChdMnzayOlMKF0yfNs8zvYHIPfHXb_ZVdMMhz_3qRPjH7xGv_kkUA0xVSIsD&_nc_ohc=Py2iJIweiWsQ7kNvgHxHEWu&_nc_zt=23&_nc_ht=scontent-lim1-1.xx&_nc_gid=AvrgyqsYWi2ihIieJuH4V4X&oh=00_AYDpWn81q6iyl7_r-OH-ojlwbxsmX4k5-bejadNfMIi8nQ&oe=674D1F1B', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2004-02-29')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Cleiner Nilson', 'n00207290@upn.pe', 'clave290204', '970276517', 'https://scontent-lim1-1.xx.fbcdn.net/v/t39.30808-6/437146542_3723668771211717_5225061011680092454_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=KmuwzqgH5wIQ7kNvgFIBU-G&_nc_zt=23&_nc_ht=scontent-lim1-1.xx&_nc_gid=AB3GDbT4OnAnGbYx8l6wrP5&oh=00_AYAxluRQVAM-BTbzRmh0Bbq8fNBihWIXXw_d6KTmGBEjbQ&oe=672B908C', 'Ingeniería de Sistemas Computacionales', 'Ciclo 9', '2001-08-30')");
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ALUMNOS (nombre, correo, clave, telefono, foto, carrera, ciclo, fecha_nacimiento) VALUES ('Angel Manuel', 'n00319747@upn.pe', 'clave170699', '915947314', 'https://scontent-lim1-1.xx.fbcdn.net/v/t1.6435-9/51508072_743952632654452_6303402168065982464_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=EyY5Yei6zOsQ7kNvgEajOZQ&_nc_zt=23&_nc_ht=scontent-lim1-1.xx&_nc_gid=AwG78_3JjWJH2QAp7A-FzOo&oh=00_AYA85airscZ2xnU3Uy_38s88oLoqApb5gXiuIFwQZi00Fw&oe=674D28B5', 'Ingeniería de Sistemas Computacionales', 'Ciclo 8', '1999-06-17')");


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

        // Insertar registros iniciales en codigosQR
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CODIGOSQR (curso_id, codigo, fecha) VALUES (1, 10003, '2023-11-22 17:55:00');") // Curso Desarrollo de Aplicaciones Móviles
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CODIGOSQR (curso_id, codigo, fecha) VALUES (1, 10004, '2023-11-29 18:00:00');") // Curso Desarrollo de Aplicaciones Móviles
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CODIGOSQR (curso_id, codigo, fecha) VALUES (3, 10007, '2023-11-21 09:13:00');") // Curso Redes 2
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CODIGOSQR (curso_id, codigo, fecha) VALUES (3, 10005, '2023-11-28 09:15:00');") // Curso Redes 2
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_CODIGOSQR (curso_id, codigo, fecha) VALUES (3, 10009, '2023-11-14 09:10:00');") // Curso Redes 2

        // Insertar registros iniciales en asistencias
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 1, 1, '2023-11-22 17:55:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 1, 2, '2023-11-29 18:00:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 3, 3, '2023-11-21 09:13:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 3, 4, '2023-11-28 09:15:00');")
        sqLiteDatabase.execSQL("INSERT INTO $TABLE_ASISTENCIAS (usuario_id, curso_id, codigo, fecha) VALUES (1, 3, 5, '2023-11-14 09:10:00');")

    }


    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_PROFESORES")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_CURSOS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_INSCRIPCIONES")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_CODIGOSQR")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_ASISTENCIAS")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_HORARIOS")

        onCreate(sqLiteDatabase)
    }
}
