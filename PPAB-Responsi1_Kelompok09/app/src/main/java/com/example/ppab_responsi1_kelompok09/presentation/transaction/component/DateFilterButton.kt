package com.example.ppab_responsi1_kelompok09.presentation.transaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.DateFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.getDateRange
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun DateFilterButton(
    onClickShowOverlay: () -> Unit,
    selectedFilter: DateFilter
) {
    val range = getDateRange(selectedFilter)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .clickable(onClick = { onClickShowOverlay() })
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_date_range),
                contentDescription = null,
                tint = Dark,
                modifier = Modifier
                    .size(20.dp)
            )
            AppText(
                text = range,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
        AppText(
            text = "Atur",
            color = Primary,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}