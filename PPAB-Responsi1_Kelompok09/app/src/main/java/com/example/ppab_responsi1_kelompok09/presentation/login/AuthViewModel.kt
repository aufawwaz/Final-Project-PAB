package com.example.ppab_responsi1_kelompok09.presentation.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.getOnboardingState
import com.example.ppab_responsi1_kelompok09.data.local.TokenDataStore
import com.example.ppab_responsi1_kelompok09.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.domain.model.LoginRequest
//import com.example.ppab_responsi1_kelompok09.domain.repository.UserRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    //private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val tokenDataStore = TokenDataStore.getInstance(context)

    private val authApi = RetrofitInstance.api  // Langsung pakai Auth API dari Laravel

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Unauthenticated)
    val authState: StateFlow<AuthUiState> = _authState

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    var isLogin by mutableStateOf(false)
        private set

    var onboardingHasOpened by mutableStateOf(false)
        private set

    var isInitialized by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            isLogin = getSavedLoginState()
            fetchProfile()
            onboardingHasOpened = getOnboardingState(context)
            isInitialized = true
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApi.login(LoginRequest(email, password))
                val token = response.token
                tokenDataStore.saveToken(token)
                println("Token saved to DataStore: $token")
                saveLoginState(true)
                fetchProfile()
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error("Login failed: ${e.message}")
            }
        }
    }

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val token = tokenDataStore.getToken.first()
                println("Token fetched from DataStore: $token")
                if (!token.isNullOrBlank()) {
                    val response = authApi.getProfile("Bearer $token")
                    _user.value = response.user.toDomain()
                    _authState.value = AuthUiState.Authenticated
                } else {
                    _authState.value = AuthUiState.Error("Token kosong, silakan login ulang.")
                    signOut()
                }
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error("Gagal mengambil token atau profil: ${e.message}")
                signOut()
            }
        }
    }

//    // Token helpers
//    private fun saveToken(token: String) {
//        prefs.edit().putString("token", token).apply()
//        println("Token disimpan: $token") // Logging token yang disimpan
//    }
//
//    private fun getSavedToken(): String? {
//        val token = prefs.getString("token", null)
//        println("Token diambil dari SharedPreferences: $token") // Logging token yang diambil
//        return token
//    }
//
//    private fun clearToken() {
//        prefs.edit().remove("token").apply()
//    }
//
    private suspend fun saveLoginState(isLogin: Boolean) {
        tokenDataStore.saveLoginState(isLogin)
    }

    private suspend fun getSavedLoginState(): Boolean {
        return tokenDataStore.getLoginState.first()
    }

    fun signOut() {
        viewModelScope.launch {
            tokenDataStore.clearAll()
            _user.value = null
            _authState.value = AuthUiState.Unauthenticated
            isLogin = false
        }
    }

    fun seenOnboarding() {
        onboardingHasOpened = true
//        viewModelScope.launch {
//            seenOnboardingState(context) // ini menyimpan state ke local storage
//        }
    }
}


//import android.annotation.SuppressLint
//import android.app.Application
//import android.util.Log
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.getValue
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.ppab_responsi1_kelompok09.data.getOnboardingState
//import com.example.ppab_responsi1_kelompok09.data.seenOnboardingState
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FieldValue
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class AuthViewModel (application: Application) : AndroidViewModel(application) {
//    @SuppressLint("StaticFieldLeak")
//    private val context = application.applicationContext
//
//    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
//
//    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()
//
//    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Unauthenticated)
//    val authState: StateFlow<AuthUiState> = _authState
//
//    private val _userName = MutableStateFlow("")
//    val userName: StateFlow<String> = _userName
//
//    var onboardingHasOpened by mutableStateOf(false)
//        private set
//
//    var isInitialized by mutableStateOf(false)
//        private set
//
//    var isLogin by mutableStateOf(false)
//        private set
//
//    init {
//        checkAuthStatus()
//        viewModelScope.launch {
//            onboardingHasOpened = getOnboardingState(context)
//            isLogin = auth.currentUser != null
//            isInitialized = true
//        }
//    }
//
//    fun seenOnboarding() {
//        onboardingHasOpened = true
//        viewModelScope.launch {
//            seenOnboardingState(context)
//        }
//    }
//
//    fun checkAuthStatus() {
//        if (auth.currentUser == null) {
//            _authState.value = AuthUiState.Unauthenticated
//        } else {
//            _authState.value = AuthUiState.Authenticated
//        }
//    }
//
//    fun login(email: String, password: String) {
//
//        if (email.isEmpty() || password.isEmpty()) {
//            _authState.value = AuthUiState.Error("Email or password can't be empty")
//            return
//        }
//
//        _authState.value = AuthUiState.Loading
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _authState.value = AuthUiState.Authenticated
//                    val uid = auth.currentUser?.uid
//                    if (uid != null) {
//                        fetchUserData(uid)
//                    }
//                } else {
//                    _authState.value = AuthUiState.Error(task.exception?.message?:"Something Went Wrong")
//                }
//            }
//    }
//
//    fun signUp(email : String, password: String, username: String) {
//
//        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
//            _authState.value = AuthUiState.Error("All fields must be filled")
//            return
//        }
//
//        _authState.value = AuthUiState.Loading
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    val userId = user?.uid ?: return@addOnCompleteListener
//
//                    val userMap = hashMapOf(
//                        "name" to username,
//                        "email" to email,
//                        "created_at" to FieldValue.serverTimestamp()
//                    )
//
//                    firestore.collection("users").document(userId)
//                        .set(userMap)
//                        .addOnCompleteListener {
//                            _authState.value = AuthUiState.Authenticated
//                            fetchUserData(userId)
//                        }
//                        .addOnFailureListener { e ->
//                            _authState.value = AuthUiState.Error("Sign up succeeded but failed to save user data: ${e.message}")
//                        }
//                } else {
//                    _authState.value = AuthUiState.Error(task.exception?.message?:"Something Went Wrong")
//                }
//            }
//    }
//
//    fun signOut() {
//        auth.signOut()
//        _authState.value = AuthUiState.Unauthenticated
//    }
//
//    fun fetchUserData(userId : String) {
//        firestore.collection("users")
//            .document(userId)
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val document = task.result
//                    if (document != null && document.exists()) {
//                        val username = document.getString("name") ?: ""
//                        _userName.value = username
//                    }
//                } else {
//                    Log.e("Firestore", "Failed to fetch user data: ${task.exception?.message}")
//                }
//            }
//    }
//}