package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun CustomCheckbox(
    checkBoxValue : Boolean,
    onCheckedChange : (Boolean) -> Unit,
    scaleSp : TextUnit)
{
    val density = LocalDensity.current
    var isChecked = checkBoxValue

    // konversi sp ke dp
    val scaledDp = with(density) { scaleSp.toDp() }
    Box(
        modifier = Modifier.size(12.dp),
        contentAlignment = Alignment.Center
    ){
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxColors(
                checkedCheckmarkColor = Primary,
                checkedBorderColor = Primary,
                checkedBoxColor = White,
                uncheckedCheckmarkColor = Primary,
                uncheckedBoxColor = White,
                uncheckedBorderColor = Primary,
                disabledCheckedBoxColor = Primary,
                disabledBorderColor = Primary,
                disabledUncheckedBoxColor = Primary,
                disabledUncheckedBorderColor = Primary,
                disabledIndeterminateBoxColor = Primary,
                disabledIndeterminateBorderColor = Primary
            ),
            modifier = Modifier.scale(scaledDp.value / 24f)
        )
    }
}