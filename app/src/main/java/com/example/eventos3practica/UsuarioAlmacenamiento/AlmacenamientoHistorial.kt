package com.example.eventos3practica.UsuarioAlmacenamiento

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AlmacenamientoHistorial {
    private lateinit var historialMedicoDao: HistorialMedicoDao

    fun init(context: Context) {
        val db = AppDatabase.getDatabase(context)
        historialMedicoDao = db.historialMedicoDao()
    }

    suspend fun addHistorialMedico(email: String, historialMedico: HistorialMedico) {
        withContext(Dispatchers.IO) {
            historialMedicoDao.insert(historialMedico.copy(userEmail = email))
        }
    }

    suspend fun getHistorialesMedicos(email: String): List<HistorialMedico> {
        return withContext(Dispatchers.IO) {
            historialMedicoDao.getHistorialesMedicos(email)
        }
    }

    suspend fun updateHistorialMedico(email: String, updatedHistorial: HistorialMedico) {
        withContext(Dispatchers.IO) {
            historialMedicoDao.update(updatedHistorial.copy(userEmail = email))
        }
    }

    suspend fun deleteHistorialMedico(email: String, historialMedico: HistorialMedico) {
        withContext(Dispatchers.IO) {
            historialMedicoDao.delete(historialMedico)
        }
    }
}