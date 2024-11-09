package com.example.eventos3practica.UsuarioAlmacenamiento

object AlmacenamientoHistorial {
    private val historiales = mutableMapOf<String, MutableList<HistorialMedico>>()

    fun addHistorialMedico(email: String, historialMedico: HistorialMedico) {
        if (historiales[email] == null) {
            historiales[email] = mutableListOf()
        }
        historiales[email]?.add(historialMedico)
    }

    fun getHistorialesMedicos(email: String): List<HistorialMedico>? {
        return historiales[email]
    }

    fun deleteHistorialMedico(email: String, historialMedico: HistorialMedico) {
        historiales[email]?.remove(historialMedico)
    }
}