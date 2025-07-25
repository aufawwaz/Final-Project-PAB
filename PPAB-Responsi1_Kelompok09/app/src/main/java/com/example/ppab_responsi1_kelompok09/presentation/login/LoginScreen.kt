package com.example.ppab_responsi1_kelompok09.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomCheckbox
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.InputTextForm
import com.example.ppab_responsi1_kelompok09.presentation.components.ScaleUpFullLogo
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary900
import com.example.ppab_responsi1_kelompok09.ui.theme.White

//@Preview(showBackground = true)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel){
    Box(
        Modifier
            .fillMaxSize()
            .background(White)
    ){
        Column(
            modifier = Modifier
                .background(Primary900)
                .verticalScroll(rememberScrollState())
        ){
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Primary900, Primary),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, -5f)
                        )
                    )
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(200.dp)
            ) {
                ScaleUpFullLogo( modifier = Modifier.align(Alignment.Center) )
            }
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(White),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(Modifier.height(24.dp))
                AppText("Login", 18.sp, FontWeight.W600, Dark)
                Spacer(Modifier.height(24.dp))

                LoginForm(navController, authViewModel)
            }
        }
    }
}

@Composable
private fun LoginForm(navController: NavController, authViewModel: AuthViewModel){

    var emailLogin by rememberSaveable { mutableStateOf("") }
    var passwordLogin by rememberSaveable { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when(authState) {
            is AuthUiState.Authenticated -> navController.navigate("main") { popUpTo("login") { inclusive = true } }
            is AuthUiState.Error -> Toast.makeText(context, (authState as AuthUiState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Spacer(Modifier.height(16.dp))
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 56.dp, end = 56.dp)
    ) {
        // SIGN IN GOOGLE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Gray, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(White, RoundedCornerShape(12.dp))
                .clickable { /* sign in google */ }
                .padding(horizontal = 12.dp)
                .height(44.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_google_logo),
                    contentDescription = null,
                    modifier = Modifier.width(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppText(
                    "Sign in with Google",
                    12.sp,
                    FontWeight.Bold
                )
            }
        }
        HorizontalLine()

        InputTextForm(
            value = emailLogin,
            onValueChange = { emailLogin = it },
            placeholder = "Email",
            icon = R.drawable.ic_email,
            isPassword = false
        )

        InputTextForm(
            value = passwordLogin,
            onValueChange = { passwordLogin = it },
            placeholder = "Password",
            icon = R.drawable.ic_password,
            isPassword = true
        )

        // LOGIN CONFIRMATION
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    var isRememberMe by rememberSaveable { mutableStateOf(false) }
                    CustomCheckbox(isRememberMe, { isRememberMe = it }, 15.sp)

                    Spacer(Modifier.width(7.dp))
                    AppText("Remember me", 11.sp)
                }
                AppText("Forgot Password?", 11.sp, color = Primary)
            }
            CustomButton(
                {
                    authViewModel.login(emailLogin, passwordLogin)
                },
                "Login"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                AppText("Don't have account? ", 11.sp)
                AppText(
                    "Create account",
                    11.sp,
                    color = Primary,
                    modifier = Modifier.clickable{ navController.navigate("register") }
                )
            }
        }
    }
    Spacer(Modifier.height(40.dp))
}