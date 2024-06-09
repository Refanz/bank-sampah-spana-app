package com.spana.banksampahspana.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.spana.banksampahspana.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val ID_KEY = intPreferencesKey(ID_USER)
    private val TOKEN_KEY = stringPreferencesKey(TOKEN)
    private val NAME_KEY = stringPreferencesKey(NAME)
    private val EMAIL_KEY = stringPreferencesKey(EMAIL)
    private val ROLE_KEY = stringPreferencesKey(ROLE)

    fun getAuthUser(): Flow<User> {
        return dataStore.data.map { pref ->
            val id = pref[ID_KEY] ?: 0
            val name = pref[NAME_KEY] ?: ""
            val email = pref[EMAIL_KEY] ?: ""
            val role = pref[ROLE_KEY] ?: ""

            User(id = id, role = role, email = email, name = name)
        }
    }

    fun getAuthToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveAuthUser(user: User) {
        dataStore.edit { pref ->
            pref[ID_KEY] = user.id
            pref[NAME_KEY] = user.name
            pref[EMAIL_KEY] = user.email
            pref[ROLE_KEY] = user.role
        }
    }

    suspend fun removeAuthUser() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(ID_KEY)
            preferences.remove(NAME_KEY)
            preferences.remove(EMAIL_KEY)
            preferences.remove(ROLE_KEY)
        }
    }

    companion object {

        private const val ID_USER = "id_user"
        private const val TOKEN = "token"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val ROLE = "role"

        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(AuthPreferences::class.java) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}