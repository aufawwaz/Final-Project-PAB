package com.example.ppab_responsi1_kelompok09

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.common.style.dropShadow200
import com.example.ppab_responsi1_kelompok09.data.NavItem
import com.example.ppab_responsi1_kelompok09.pages.ContactPage.ContactScreen
import com.example.ppab_responsi1_kelompok09.pages.HomePage.HomeScreen
import com.example.ppab_responsi1_kelompok09.pages.LoginPage.LoginScreen
import com.example.ppab_responsi1_kelompok09.pages.LoginPage.RegisterScreen
import com.example.ppab_responsi1_kelompok09.pages.MorePage.MoreScreen
import com.example.ppab_responsi1_kelompok09.pages.ProductPage.ProductScreen
import com.example.ppab_responsi1_kelompok09.pages.TransactionPage.TransactionScreen
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val dataClassLists = listOf(
        NavItem("home", R.drawable.home, R.drawable.home_fill),
        NavItem("product", R.drawable.produk, R.drawable.produk_fill),
        NavItem("transaction", R.drawable.transaction, R.drawable.transaction_fill),
        NavItem("contact", R.drawable.pelanggan, R.drawable.pelanggan_fill),
        NavItem("more", R.drawable.dashboard, R.drawable.dashboard_fill)
    )
    val otherScreen = listOf("login", "register")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedColor = Primary
    val unselectedColor = Gray
    val navbarColor = White
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


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,

        // Bottom bar ga muncul di screen dalam otherScreen
        bottomBar = {
            if (currentRoute !in otherScreen) {
                NavigationBar(
                    modifier = Modifier.dropShadow200(0.dp),
                    containerColor = navbarColor
                ) {
                    dataClassLists.forEach { navItem ->
                        val isSelected = currentRoute == navItem.route
                        val iconRes = if (isSelected) navItem.selectedIcon else navItem.icon
                        val tintColor = if (isSelected) selectedColor else unselectedColor

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = null,
                                    tint = tintColor,
                                    modifier = Modifier.size(28.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = selectedColor,
                                unselectedIconColor = unselectedColor,
                                selectedTextColor = selectedColor,
                                unselectedTextColor = unselectedColor
                            )
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("home") { HomeScreen(navController) }
            composable("product") { ProductScreen(navController) }
            composable("transaction") { TransactionScreen(navController) }
            composable("contact") { ContactScreen(navController) }
            composable("more") { MoreScreen(navController) }

            composable("login") { LoginScreen(navController) }
            composable("register") { RegisterScreen(navController) }
        }
    }
}