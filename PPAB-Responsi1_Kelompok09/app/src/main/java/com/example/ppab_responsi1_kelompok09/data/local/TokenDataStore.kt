package com.example.ppab_responsi1_kelompok09.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//val Context.dataStore by preferencesDataStore(name = "user_prefs")

class TokenDataStore private constructor(private val context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: TokenDataStore? = null
        private val LOGIN_KEY = booleanPreferencesKey("is_login")

        fun getInstance(context: Context): TokenDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenDataStore(context.applicationContext).also { INSTANCE = it }
            }
        }

        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    val getToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveLoginState(isLogin: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = isLogin
        }
    }

    val getLoginState: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[LOGIN_KEY] ?: false }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(LOGIN_KEY)
        }
    }
}