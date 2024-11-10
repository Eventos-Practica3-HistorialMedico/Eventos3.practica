# Eventos3 Practica

## Proyecto realizado por Jose Daniel Martín, Hugo Sanchez, Fernando Santamaría y Jose Antonio Oyono

## Enlace del repositorio --> [https://github.com/Eventos-Practica3-HistorialMedico/Eventos3.practica.git)

Este proyecto de Android en Kotlin implementa una aplicación para gestionar el historial médico de los usuarios. Permite a los usuarios iniciar sesión, agregar, actualizar, eliminar y compartir su historial médico. La interfaz está desarrollada usando Jetpack Compose.

## Estructura de Clases

### 1. MainActivity

`MainActivity` es la actividad principal de la aplicación. Inicializa el almacenamiento de historial y el gestor de usuarios, y configura la interfaz de usuario.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlmacenamientoHistorial.init(this)
        UserManager.init(this)
        setContent {
            Eventos3practicaTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                onLogin = { username, password, showMessage ->
                                    val user = UserManager.getUserByUsername(username, password)
                                    if (user != null) {
                                        showMessage("Login exitoso")
                                    } else {
                                        showMessage("Usuario no encontrado")
                                    }
                                },
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("historial/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            HistorialScreen(email = username, modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}
```

#### Métodos

- `onCreate`: Inicializa el almacenamiento de historial y el gestor de usuarios. Configura la interfaz de usuario y carga `LoginScreen` y `HistorialScreen`.

### 2. HistorialScreen

`HistorialScreen` es una función `@Composable` que define la interfaz de usuario para gestionar el historial médico, permitiendo al usuario agregar, actualizar, eliminar y compartir su historial médico.

```kotlin
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
```

#### Propiedades

- `fecha`: Fecha del historial médico ingresada por el usuario.
- `descripcion`: Descripción del historial médico.
- `diagnostico`: Diagnóstico del historial médico.
- `tratamiento`: Tratamiento del historial médico.
- `message`: Mensaje de estado para mostrar al usuario.
- `historial`: Lista de historiales médicos obtenida del almacenamiento.
- `selectedHistorial`: Historial médico seleccionado para edición.

#### Elementos de la Interfaz

- `BasicTextField`: Campos para ingresar la fecha, descripción, diagnóstico y tratamiento del historial médico.
- `Button`: Botones para guardar, actualizar, mostrar y compartir el historial médico.
- `Text`: Muestra mensajes de estado.
- `LazyColumn`: Lista dinámica que muestra los historiales médicos.

#### Métodos

- `onClick` (dentro de `Button`): Guarda o actualiza el historial médico en el almacenamiento.
- `LaunchedEffect`: Configura un listener para obtener los historiales médicos del almacenamiento.

### 3. HistorialMedico

`HistorialMedico` es una clase de datos que representa un historial médico.

```kotlin
@Entity(tableName = "historial_medico")
data class HistorialMedico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val fecha: String?,
    val descripcion: String?,
    val diagnostico: String?,
    val tratamiento: String?
)
```

#### Propiedades

- `id`: Identificador único del historial médico.
- `userEmail`: Correo electrónico del usuario.
- `fecha`: Fecha del historial médico.
- `descripcion`: Descripción del historial médico.
- `diagnostico`: Diagnóstico del historial médico.
- `tratamiento`: Tratamiento del historial médico.

## Dependencias Externas

- `AlmacenamientoHistorial`: Proporciona métodos para agregar, actualizar, eliminar y obtener historiales médicos.
- `UserManager`: Gestiona los usuarios y proporciona métodos para obtener usuarios por nombre de usuario y contraseña.

## Ejecución y Funcionalidad

La aplicación permite al usuario:

- Ingresar la fecha, descripción, diagnóstico y tratamiento de un historial médico.
- Guardar, actualizar y eliminar historiales médicos.
- Visualizar la lista de historiales médicos almacenados.
- Compartir el historial médico con un médico.
