package com.example.ppab_responsi1_kelompok09.presentation.login

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.ppab_responsi1_kelompok09.common.component.CustomButton
import com.example.ppab_responsi1_kelompok09.common.component.CustomCheckbox
import com.example.ppab_responsi1_kelompok09.common.component.HorizontalLine
import com.example.ppab_responsi1_kelompok09.common.component.InputTextForm
import com.example.ppab_responsi1_kelompok09.common.component.ScaleUpFullLogo
import com.example.ppab_responsi1_kelompok09.common.style.AppText
import com.example.ppab_responsi1_kelompok09.data.Users
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary900
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.example.ppab_responsi1_kelompok09.view_model.UserViewModel
import kotlinx.coroutines.launch

// @Preview(showBackground = true)
@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel){
    Box(
        Modifier.fillMaxSize().background(White)
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
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(White),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(Modifier.height(24.dp))
                AppText("Register", 18.sp, FontWeight.W600, MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(24.dp))

                RegisterForm(navController, userViewModel)
            }
        }

    }
}

@Composable
private fun RegisterForm(navController: NavController, userViewModel: UserViewModel){
    val context = LocalContext.current // context user data
    val coroutineScope = rememberCoroutineScope()

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
                .background(White, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp)
                .height(44.dp)
                .clickable { /* sign in google */ },
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
        var usernameRValue by rememberSaveable { mutableStateOf("") }
        InputTextForm(
            usernameRValue,
            { usernameRValue = it },
            "Username",
            R.drawable.ic_login,
            false
        )

        var emailRValue by rememberSaveable { mutableStateOf("") }
        InputTextForm(
            emailRValue, { emailRValue = it },
            "Email",
            R.drawable.ic_email,
            false
        )

        var passwordRValue by rememberSaveable { mutableStateOf("") }
        InputTextForm(
            passwordRValue,
            { passwordRValue = it },
            "Password",
            R.drawable.ic_password,
            true
        )

        // REGISTER CONFIRMATION
        var isAgreeToTnC by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                CustomCheckbox(isAgreeToTnC, { isAgreeToTnC = it }, 15.sp)
                Spacer(Modifier.width(7.dp))
                AppText("I agree to the ", 11.sp)
                AppText("Terms & Conditions", 11.sp, color = Primary)
            }
            CustomButton(
                {
                    if (isAgreeToTnC &&
                        Users.isValidUsername(usernameRValue) &&
                        Users.isValidEmail(emailRValue) &&
                        Users.isValidPassword(passwordRValue))
                    {
                        coroutineScope.launch {
                            Users.addUser(context, usernameRValue, emailRValue, passwordRValue)
                            navController.navigate("login")
                        }
                    }
                },
                "Sign Up"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                AppText("Already have an account? ", 11.sp)
                AppText(
                    "Sign In",
                    11.sp,
                    color = Primary,
                    modifier = Modifier.clickable{ navController.navigate("login") }
                )
            }
        }
    }
    Spacer(Modifier.height(40.dp))
}