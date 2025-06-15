package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Buat dapetin tanggal
fun getDateRangeValue(filter: DateFilter): Pair<LocalDate, LocalDate> {
    val today = LocalDate.now()
    return when (filter) {
        DateFilter.TODAY -> today to today
        DateFilter.YESTERDAY -> {
            val yesterday = today.minusDays(1)
            yesterday to yesterday
        }
        DateFilter.THIS_WEEK -> {
            val startOfWeek = today.with(java.time.DayOfWeek.MONDAY)
            val endOfWeek = startOfWeek.plusDays(6)
            startOfWeek to endOfWeek
        }
        DateFilter.THIS_MONTH -> {
            val startOfMonth = today.withDayOfMonth(1)
            val endOfMonth = today.withDayOfMonth(today.lengthOfMonth())
            startOfMonth to endOfMonth
        }
    }
}

fun getPreviousDateRange(filter: DateFilter): Pair<LocalDate, LocalDate> {
    val (start, end) = getDateRangeValue(filter)
    return when (filter) {
        DateFilter.TODAY -> {
            val prev = start.minusDays(1)
            prev to prev
        }
        DateFilter.YESTERDAY -> {
            val prev = start.minusDays(2)
            prev to prev
        }
        DateFilter.THIS_WEEK -> {
            val prevStart = start.minusWeeks(1)
            val prevEnd = end.minusWeeks(1)
            prevStart to prevEnd
        }
        DateFilter.THIS_MONTH -> {
            val prevMonth = start.minusMonths(1)
            val prevStart = prevMonth.withDayOfMonth(1)
            val prevEnd = prevMonth.withDayOfMonth(prevMonth.lengthOfMonth())
            prevStart to prevEnd
        }
    }
}

fun getPrevPeriodLabel(filter: DateFilter): String {
    return when (filter) {
        DateFilter.TODAY -> "Dari Kemarin"
        DateFilter.YESTERDAY -> "Dari Dua hari lalu"
        DateFilter.THIS_WEEK -> "Dari Minggu lalu"
        DateFilter.THIS_MONTH -> "Dari Bulan lalu"
    }
}

fun getDateRange(filter: DateFilter): String {
    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    val (start, end) = getDateRangeValue(filter)
    return if (start == end) {
        start.format(dateFormatter)
    } else {
        "${start.format(dateFormatter)} - ${end.format(dateFormatter)}"
    }
}

// Buat klasifikasi tanggal
enum class DateFilter (val label : String) {
    TODAY("Hari Ini"),
    YESTERDAY("Kemarin"),
    THIS_WEEK("Minggu Ini"),
    THIS_MONTH("Bulan Ini")
}

@Composable
fun DateFilterOverlay (
    onDismiss: () -> Unit = {},
    onSelected: (DateFilter) -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .width(316.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(White)
                .padding(vertical = 16.dp)
        ) {
            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    AppText(
                        text = "Pilih Tanggal",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
                DateFilter.entries.forEach { filter->
                    Column (
                        modifier = Modifier
                            .clickable{
                                onSelected(filter)
                                onDismiss()
                            }
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                    ) {
                        AppText(
                            text = filter.label,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                        AppText(
                            text = getDateRange(filter),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}