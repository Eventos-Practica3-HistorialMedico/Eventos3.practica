package com.example.eventos3practica.Historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventos3practica.UsuarioAlmacenamiento.AlmacenamientoHistorial
import com.example.eventos3practica.UsuarioAlmacenamiento.HistorialMedico

@Composable
fun HistorialScreen(email: String, modifier: Modifier = Modifier) {
    val fecha = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }
    val diagnostico = remember { mutableStateOf("") }
    val tratamiento = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = fecha.value,
                onValueChange = { fecha.value = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (fecha.value.isEmpty()) {
                        Text("Fecha")
                    }
                    innerTextField()
                }
            )
            BasicTextField(
                value = descripcion.value,
                onValueChange = { descripcion.value = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (descripcion.value.isEmpty()) {
                        Text("Descripción")
                    }
                    innerTextField()
                }
            )
            BasicTextField(
                value = diagnostico.value,
                onValueChange = { diagnostico.value = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (diagnostico.value.isEmpty()) {
                        Text("Diagnóstico")
                    }
                    innerTextField()
                }
            )
            BasicTextField(
                value = tratamiento.value,
                onValueChange = { tratamiento.value = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (tratamiento.value.isEmpty()) {
                        Text("Tratamiento")
                    }
                    innerTextField()
                }
            )
            Button(onClick = {
                val historialMedico = HistorialMedico(
                    id = null,
                    fecha = fecha.value,
                    descripcion = descripcion.value,
                    diagnostico = diagnostico.value,
                    tratamiento = tratamiento.value
                )
                AlmacenamientoHistorial.addHistorialMedico(email, historialMedico)
                message.value = "Historial guardado exitosamente"
            }) {
                Text("Guardar")
            }
            if (message.value.isNotEmpty()) {
                Text(message.value)
            }
        }
    }
}