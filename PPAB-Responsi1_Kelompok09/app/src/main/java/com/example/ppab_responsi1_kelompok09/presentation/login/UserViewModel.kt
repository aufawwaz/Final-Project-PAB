package com.example.ppab_responsi1_kelompok09.presentation.login

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.Users
import com.example.ppab_responsi1_kelompok09.data.getLoginData
import com.example.ppab_responsi1_kelompok09.data.getOnboardingState
import com.example.ppab_responsi1_kelompok09.data.saveLoginData
import com.example.ppab_responsi1_kelompok09.data.seenOnboardingState
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak") // ga tau apa ini tapi applicationContext butuh supress
    private val context = application.applicationContext

    var onboardingHasOpened by mutableStateOf(false)
        private set

    var isLogin by mutableStateOf(false)
        private set

    var username by mutableStateOf("")
        private set

    var isInitialized by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            val (loginState, user) = getLoginData(context)
            isLogin = loginState
            username = user

            val onboardingState = getOnboardingState(context)
            onboardingHasOpened = onboardingState
            
            isInitialized = true
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            Users.loadUsers(context)

            val user = Users.login(email, password)
            if (user != null) {
                saveLogin(true, user.username)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun saveLogin(isLogin: Boolean, username: String) {
        viewModelScope.launch {
            saveLoginData(context, isLogin, username)
            this@UserViewModel.isLogin = isLogin
            this@UserViewModel.username = username
        }
    }

    fun logout() {
        saveLogin(false, "")
    }

    fun seenOnboarding(){
        onboardingHasOpened = true
        viewModelScope.launch {
            seenOnboardingState(context)
        }
    }
}