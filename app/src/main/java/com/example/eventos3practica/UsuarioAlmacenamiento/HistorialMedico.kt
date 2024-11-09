package com.example.eventos3practica.UsuarioAlmacenamiento

class HistorialMedico {
    var id: String? = null
        private set
    var fecha: String? = null
        private set
    var descripcion: String? = null
        private set
    var diagnostico: String? = null
        private set
    var tratamiento: String? = null
        private set

    constructor()

    constructor(
        id: String?,
        fecha: String?,
        descripcion: String?,
        diagnostico: String?,
        tratamiento: String?
    ) {
        this.id = id
        this.fecha = fecha
        this.descripcion = descripcion
        this.diagnostico = diagnostico
        this.tratamiento = tratamiento
    }
}