package com.example.eventos3practica.UsuarioAlmacenamiento

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.Embedded

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val user: String?,
    val password: String?,
    val phone: String?,
    val address: String?
)

data class UserWithHistorialMedico(
    @Embedded val user: User,
    @Relation(
        parentColumn = "email",
        entityColumn = "userEmail"
    )
    val historialMedico: List<HistorialMedico>
)