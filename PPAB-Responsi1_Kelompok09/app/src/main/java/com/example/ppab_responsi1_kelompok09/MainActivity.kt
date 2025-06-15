package com.example.ppab_responsi1_kelompok09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.presentation.login.LoginScreen
import com.example.ppab_responsi1_kelompok09.presentation.login.RegisterScreen
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.ScaleUpTheme
import com.example.ppab_responsi1_kelompok09.presentation.login.AuthViewModel
import com.example.ppab_responsi1_kelompok09.presentation.onboard.OnboardingScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val loginNavController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()

            ScaleUpTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Dark.copy(0.3f),
                        darkIcons = false
                    )
                    systemUiController.setNavigationBarColor(
                        color = Dark,
                        darkIcons = false
                    )
                }

                NavHost(
                    navController = loginNavController,
                    startDestination = "main" // defaultnya adalah 'splash' tetapi karena error ganti jadi main dulu
                ) {
                    composable("splash") {
                        LaunchedEffect(authViewModel.isInitialized) {
                            if (!authViewModel.isInitialized) return@LaunchedEffect

                            delay(500)
                            if (authViewModel.isLogin) {
                                loginNavController.navigate("main") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            } else if(!authViewModel.onboardingHasOpened){
                                loginNavController.navigate("onboarding"){
                                    popUpTo("splash") { inclusive = true }
                                }
                            } else {
                                loginNavController.navigate("login") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        }
                    }
                    composable("onboarding") { OnboardingScreen(loginNavController, authViewModel) }
                    composable("register") { RegisterScreen(loginNavController, authViewModel) }
                    composable("login") { LoginScreen(loginNavController, authViewModel) }
                    composable("main") { MainNavigation(loginNavController, authViewModel) }
                }
            }
        }
    }
}