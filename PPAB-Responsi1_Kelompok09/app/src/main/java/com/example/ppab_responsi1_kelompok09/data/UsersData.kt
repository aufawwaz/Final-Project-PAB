package com.example.ppab_responsi1_kelompok09.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ppab_responsi1_kelompok09.domain.model.User
import com.example.ppab_responsi1_kelompok09.data.local.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.*
import kotlinx.serialization.json.*

//// DataStore Extension
//val Context.dataStore by preferencesDataStore(name = "user_prefs")
//
//// key (untuk data di user_prefs)
//private val USER_LIST_KEY = stringPreferencesKey("user_list")
//private val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
//private val USERNAME_KEY = stringPreferencesKey("name")
private val ONBOARDING_KEY = booleanPreferencesKey("onboarding")
//
//class Users {
//    companion object {
//        private var listUser = mutableListOf<User>()
//
//        suspend fun loadUsers(context: Context) {
//            listUser = getUserList(context).toMutableList()
//        }
//
//        suspend fun saveUsers(context: Context) {
//            saveUserList(context, listUser)
//        }
//
//        suspend fun addUser(context: Context, username: String, email: String, password: String) {
//            val newUser = User(username, email, password)
//            listUser.add(newUser)
//            saveUsers(context)
//        }
//
//        fun isValidUsername(username: String): Boolean = username.length > 3
//
//        fun isValidEmail(email: String): Boolean =
//            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//
//        fun isValidPassword(password: String): Boolean = password.length > 3
//
//        // HANYA cek valid login, tidak set state
//        fun login(email: String, password: String): User? {
//            return listUser.firstOrNull { it.email == email && it.password == password }
//        }
//    }
//}
//
//// simpan list data
//suspend fun saveUserList(context: Context, users: List<User>) {
//    val json = Json.encodeToString(users)
//    context.dataStore.edit { prefs ->
//        prefs[USER_LIST_KEY] = json
//    }
//}
//
//suspend fun getUserList(context: Context): List<User> {
//    val prefs = context.dataStore.data.first()
//    val json = prefs[USER_LIST_KEY] ?: "[]"
//    return Json.decodeFromString(json)
//}
//
//// simpan dan ambil data saat ini
//suspend fun saveLoginData(context: Context, isLogin: Boolean, username: String) {
//    context.dataStore.edit { prefs ->
//        prefs[IS_LOGIN_KEY] = isLogin
//        prefs[USERNAME_KEY] = username
//    }
//}
//
//suspend fun getLoginData(context: Context): Pair<Boolean, String> {
//    val prefs = context.dataStore.data.first()
//    val isLogin = prefs[IS_LOGIN_KEY] ?: false
//    val username = prefs[USERNAME_KEY] ?: ""
//    return Pair(isLogin, username)
//}

suspend fun seenOnboardingState(context: Context){
    context.dataStore.edit { prefs ->
        prefs[ONBOARDING_KEY] = true
        Log.d("DEBUG", "Onboarding selesai, diset true")

    }
}

suspend fun getOnboardingState(context: Context): Boolean{
    val prefs = context.dataStore.data.first()
    return prefs[ONBOARDING_KEY] ?: false
}