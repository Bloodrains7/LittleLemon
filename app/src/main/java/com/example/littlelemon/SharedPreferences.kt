package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object LittleLemonPreferences {
    const val KEY_FIRST_NAME = "firstName"
    const val KEY_LAST_NAME = "lastName"
    const val KEY_EMAIL = "email"

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    }

    fun saveUserData(firstName: String, lastName: String, email: String) {
        preferences.edit(commit = true) {
            putString(KEY_FIRST_NAME, firstName)
            putString(KEY_LAST_NAME, lastName)
            putString(KEY_EMAIL, email)
        }
    }

    fun getUserFirstName(): String {
        return preferences.getString(KEY_FIRST_NAME, "") ?: ""
    }

    fun getUserLastName(): String {
        return preferences.getString(KEY_LAST_NAME, "") ?: ""
    }

    fun getUserEmail(): String {
        return preferences.getString(KEY_EMAIL, "") ?: ""
    }

    fun isUserRegistered(): Boolean {
        return preferences.getString(KEY_FIRST_NAME, null) != null
    }

    fun clearUserData() {
        preferences.edit(commit = true) {
            remove(KEY_FIRST_NAME)
            remove(KEY_LAST_NAME)
            remove(KEY_EMAIL)
        }
    }
}