package com.example.proyectoappv3.SQLite.DB.Entidades

data class Curso(
    var id: Int = 0,
    var nombreCurso: String,
    var profesorId: Int,
    var salon: String
)
