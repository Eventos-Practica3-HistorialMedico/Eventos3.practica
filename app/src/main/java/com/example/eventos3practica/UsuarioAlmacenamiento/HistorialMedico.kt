package com.example.eventos3practica.UsuarioAlmacenamiento

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_medico")
data class HistorialMedico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val fecha: String?,
    val descripcion: String?,
    val diagnostico: String?,
    val tratamiento: String?
)