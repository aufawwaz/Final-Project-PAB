package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun ScaleUpFullLogo(modifier : Modifier = Modifier, size: TextUnit = 39.sp, color: Color = White) {
    ConstraintLayout (
        modifier = modifier
    ){
        val (logo, cale, up) = createRefs()
        val density = LocalDensity.current
        val scaledDp = with(density) { size.toDp() }

        Icon(
            painter = painterResource(R.drawable.img_scaleup_logo),
            contentDescription = "Logo",
            tint = color,
            modifier = Modifier
                .size(scaledDp * 1.4f)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        AppText(
            text = "cale",
            fontSize = size,
            color = color,
            modifier = Modifier
                .constrainAs(cale) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(logo.end, margin = -(scaledDp * 0.2f))
                }
        )
        AppText(
            text = "Up",
            fontSize = size,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier
                .constrainAs(up) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(cale.end)
                }
        )
    }
}