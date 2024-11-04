package com.example.proyectoappv3

import com.example.proyectoappv3.SQLite.DB.Entidades.Alumno
import com.example.proyectoappv3.SQLite.DB.Entidades.Profesor

object UserSession {
    var currentUser: Alumno? = null
    var currentTeacher: Profesor? = null
}