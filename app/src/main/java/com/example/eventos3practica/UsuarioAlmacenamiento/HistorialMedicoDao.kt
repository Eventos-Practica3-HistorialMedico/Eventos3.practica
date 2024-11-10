package com.example.eventos3practica.UsuarioAlmacenamiento

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface HistorialMedicoDao {
    @Insert
    fun insert(historialMedico: HistorialMedico)

    @Update
    fun update(historialMedico: HistorialMedico)

    @Delete
    fun delete(historialMedico: HistorialMedico)

    @Query("SELECT * FROM historial_medico WHERE userEmail = :email AND fecha = :fecha AND descripcion = :descripcion AND diagnostico = :diagnostico AND tratamiento = :tratamiento")
    fun findDuplicate(email: String, fecha: String?, descripcion: String?, diagnostico: String?, tratamiento: String?): HistorialMedico?

    @Query("SELECT * FROM historial_medico WHERE userEmail = :email")
    fun getHistorialesMedicos(email: String): List<HistorialMedico>
}