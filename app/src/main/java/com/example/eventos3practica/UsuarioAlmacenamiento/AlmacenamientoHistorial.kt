package com.example.eventos3practica.UsuarioAlmacenamiento

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

object AlmacenamientoHistorial {
    private lateinit var historialMedicoDao: HistorialMedicoDao

    fun init(context: Context) {
        val db = AppDatabase.getDatabase(context)
        historialMedicoDao = db.historialMedicoDao()
    }

    suspend fun addHistorialMedico(email: String, historialMedico: HistorialMedico): String {
        return withContext(Dispatchers.IO) {
            try {
                validateHistorial(historialMedico)
                validateDate(historialMedico.fecha)
                val duplicate = historialMedicoDao.findDuplicate(
                    email, historialMedico.fecha, historialMedico.descripcion, historialMedico.diagnostico, historialMedico.tratamiento
                )
                if (duplicate != null) {
                    "Entrada duplicada encontrada"
                } else {
                    historialMedicoDao.insert(historialMedico.copy(userEmail = email))
                    "Historial agregado exitosamente"
                }
            } catch (e: Exception) {
                e.message ?: "Ocurrió un error"
            }
        }
    }

    private fun validateDate(date: String?) {
        if (date != null) {
            val datePattern = Regex("^\\d{4}-\\d{2}-\\d{2}$")
            if (!datePattern.matches(date)) {
                throw IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd")
            }
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date) ?: throw IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd")
        }
    }

    private fun validateHistorial(historialMedico: HistorialMedico) {
        if (historialMedico.fecha.isNullOrEmpty() ||
            historialMedico.descripcion.isNullOrEmpty() ||
            historialMedico.diagnostico.isNullOrEmpty() ||
            historialMedico.tratamiento.isNullOrEmpty()) {
            throw IllegalArgumentException("Todos los campos del historial médico son obligatorios")
        }
    }

    suspend fun getHistorialesMedicos(email: String): List<HistorialMedico> {
        return withContext(Dispatchers.IO) {
            historialMedicoDao.getHistorialesMedicos(email)
        }
    }

    suspend fun updateHistorialMedico(email: String, updatedHistorial: HistorialMedico): String {
        return withContext(Dispatchers.IO) {
            try {
                validateHistorial(updatedHistorial)
                validateDate(updatedHistorial.fecha)
                val duplicate = historialMedicoDao.findDuplicate(
                    email, updatedHistorial.fecha, updatedHistorial.descripcion, updatedHistorial.diagnostico, updatedHistorial.tratamiento
                )
                if (duplicate != null && duplicate.id != updatedHistorial.id) {
                    "Entrada duplicada encontrada"
                } else {
                    historialMedicoDao.update(updatedHistorial.copy(userEmail = email))
                    "Historial actualizado exitosamente"
                }
            } catch (e: Exception) {
                e.message ?: "Ocurrió un error"
            }
        }
    }

    suspend fun deleteHistorialMedico(email: String, historialMedico: HistorialMedico): String {
        return withContext(Dispatchers.IO) {
            try {
                historialMedicoDao.delete(historialMedico)
                "Historial eliminado exitosamente"
            } catch (e: Exception) {
                e.message ?: "Ocurrió un error"
            }
        }
    }
}