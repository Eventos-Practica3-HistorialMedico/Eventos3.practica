package com.example.eventos3practica.Historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eventos3practica.UsuarioAlmacenamiento.AlmacenamientoHistorial
import com.example.eventos3practica.UsuarioAlmacenamiento.HistorialMedico
import kotlinx.coroutines.launch

@Composable
fun HistorialScreen(email: String, modifier: Modifier = Modifier) {
    val fecha = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }
    val diagnostico = remember { mutableStateOf("") }
    val tratamiento = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val historial = remember { mutableStateOf<List<HistorialMedico>?>(null) }
    val selectedHistorial = remember { mutableStateOf<HistorialMedico?>(null) }
    val coroutineScope = rememberCoroutineScope()

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
                        Text("Fecha (yyyy-MM-dd)")
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
                coroutineScope.launch {
                    try {
                        val historialMedico = HistorialMedico(
                            id = selectedHistorial.value?.id ?: 0,
                            userEmail = email,
                            fecha = fecha.value,
                            descripcion = descripcion.value,
                            diagnostico = diagnostico.value,
                            tratamiento = tratamiento.value
                        )
                        if (selectedHistorial.value == null) {
                            val result = AlmacenamientoHistorial.addHistorialMedico(email, historialMedico)
                            message.value = result
                        } else {
                            AlmacenamientoHistorial.updateHistorialMedico(email, historialMedico)
                            message.value = "Historial actualizado exitosamente"
                            selectedHistorial.value = null
                        }
                    } catch (e: Exception) {
                        message.value = e.message ?: "Ocurrió un error"
                    }
                }
            }) {
                Text(if (selectedHistorial.value == null) "Guardar" else "Actualizar")
            }
            Button(onClick = {
                coroutineScope.launch {
                    val historiales = AlmacenamientoHistorial.getHistorialesMedicos(email)
                    if (historiales.isNullOrEmpty()) {
                        message.value = "Historial vacío"
                    } else {
                        historial.value = historiales
                        message.value = ""
                    }
                }
            }) {
                Text("Mostrar Historial")
            }
            Button(onClick = {
                if (historial.value.isNullOrEmpty()) {
                    message.value = "No hay historial para compartir"
                } else {
                    message.value = "Historial compartido con el médico exitosamente"
                }
            }) {
                Text("Compartir Historial")
            }
            if (message.value.isNotEmpty()) {
                Text(message.value)
            }
            historial.value?.let {
                LazyColumn {
                    items(it) { historialMedico ->
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("Fecha: ${historialMedico.fecha}")
                            Text("Descripción: ${historialMedico.descripcion}")
                            Text("Diagnóstico: ${historialMedico.diagnostico}")
                            Text("Tratamiento: ${historialMedico.tratamiento}")
                            Row {
                                Button(onClick = {
                                    selectedHistorial.value = historialMedico
                                    fecha.value = historialMedico.fecha ?: ""
                                    descripcion.value = historialMedico.descripcion ?: ""
                                    diagnostico.value = historialMedico.diagnostico ?: ""
                                    tratamiento.value = historialMedico.tratamiento ?: ""
                                }) {
                                    Text("Editar")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    coroutineScope.launch {
                                        AlmacenamientoHistorial.deleteHistorialMedico(email, historialMedico)
                                        val updatedHistoriales = AlmacenamientoHistorial.getHistorialesMedicos(email)
                                        historial.value = updatedHistoriales
                                        message.value = "Historial eliminado exitosamente"
                                    }
                                }) {
                                    Text("Borrar")
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}