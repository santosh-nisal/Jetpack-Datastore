package com.example.jetpackdatastore

import android.content.Context
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "app_pref"
)

class AppPreferences(private val context: Context) {

    companion object {
        val STUDENT_NAME = stringPreferencesKey(name = "student_name")
    }

    suspend fun saveStudentName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[STUDENT_NAME] = name
        }
    }

    val studentName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[STUDENT_NAME] ?: ""
        }
}