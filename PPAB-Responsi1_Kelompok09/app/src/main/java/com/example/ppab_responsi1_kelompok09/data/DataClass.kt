package com.example.ppab_responsi1_kelompok09.data

import androidx.compose.ui.Modifier
import java.time.LocalDate

data class NavItem(
    val route: String,
    val icon: Int,
    val selectedIcon: Int
)

data class MenuItem(
    val label : String,
    val icon : Int
)

data class KnowledgeCardItem(
    val imageRes : Int,
    val title : String,
    val description : String
)

data class TabelItem(
    val imageRes : Int,
    val name : String,
    val date: String,
    val money : String
)

data class ProductItem(
    val onCLick : () -> Unit,
    val productImage : Int,
    val category : String,
    val productName : String,
    val sold : String,
    val stock : String,
    val price : String
)

data class Contact(
    val imageRes : Int,
    val name : String,
    val number : String
)