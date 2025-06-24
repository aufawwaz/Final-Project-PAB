package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray

@Composable
fun SearchBarFilter(
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    onSearch: (String) -> Unit
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(44.dp)
    ) {
        Box (
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            var searchFilterValue by rememberSaveable { mutableStateOf("") }
            InputTextForm(
                value = searchFilterValue,
                onValueChange = {
                    searchFilterValue = it
                    onSearch(it) // Panggil callback setiap kali nilai berubah
                },
                placeholder = placeholder,
                icon = R.drawable.ic_search,
                isPassword = false,
                isLost = true
            )
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .border(1.dp, Gray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon (
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = null,
                modifier = Modifier.width(16.dp),
                tint = Gray
            )
        }
    }
}