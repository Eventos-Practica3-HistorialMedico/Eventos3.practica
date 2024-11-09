package com.example.eventos3practica.UsuarioAlmacenamiento

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecureStorage(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUser(user: User) {
        with(sharedPreferences.edit()) {
            putString("user_email", user.email)
            putString("user_name", user.name)
            putString("user_password", user.password)
            putString("user_phone", user.phone)
            putString("user_address", user.address)
            apply()
        }
    }

    fun getUser(email: String, password: String): User? {
        val storedEmail = sharedPreferences.getString("user_email", null)
        val storedPassword = sharedPreferences.getString("user_password", null)
        return if (storedEmail == email && storedPassword == password) {
            User(
                email = storedEmail,
                name = sharedPreferences.getString("user_name", null),
                password = storedPassword,
                phone = sharedPreferences.getString("user_phone", null),
                address = sharedPreferences.getString("user_address", null),
                historialMedico = null // Manejar el historial médico por separado
            )
        } else {
            null
        }
    }
}