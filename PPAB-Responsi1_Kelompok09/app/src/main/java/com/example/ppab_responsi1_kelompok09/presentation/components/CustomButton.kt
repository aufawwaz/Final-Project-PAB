package com.example.ppab_responsi1_kelompok09.presentation.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun CustomButton(onClick: () -> Unit, text : String){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = White,
            disabledContainerColor = Gray,
            disabledContentColor = White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        AppText(text, 12.sp, FontWeight.Bold, White)
    }
}

@Composable
fun CustomButton(onClick: () -> Unit, content : @Composable () -> Unit, modifier: Modifier = Modifier){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = White,
            disabledContainerColor = Gray,
            disabledContentColor = White
        ),
        shape = RoundedCornerShape(22.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .height(44.dp)
    ) {
        content()
    }
}