package com.example.eventos3practica.UsuarioAlmacenamiento

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val user: String?,
    val password: String?,
    val phone: String?,
    val address: String?,
    val historialMedico: HistorialMedico?
)