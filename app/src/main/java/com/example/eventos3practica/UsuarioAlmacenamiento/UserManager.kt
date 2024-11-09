package com.example.eventos3practica.UsuarioAlmacenamiento

object UserManager {
    private val users = mutableListOf<User>()

    init {
        users.add(User("pepe", "pepe@example.com", "123", "1234567890", "123 Main St", null))
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUser(email: String, password: String): User? {
        return users.find { it.email == email && it.password == password }
    }

    fun addHistorialMedicoToUser(email: String, historialMedico: HistorialMedico) {
        AlmacenamientoHistorial.addHistorialMedico(email, historialMedico)
    }

    fun getHistorialesMedicosForUser(email: String): List<HistorialMedico>? {
        return AlmacenamientoHistorial.getHistorialesMedicos(email)
    }

    fun deleteHistorialMedicoFromUser(email: String, historialMedico: HistorialMedico) {
        AlmacenamientoHistorial.deleteHistorialMedico(email, historialMedico)
    }
}