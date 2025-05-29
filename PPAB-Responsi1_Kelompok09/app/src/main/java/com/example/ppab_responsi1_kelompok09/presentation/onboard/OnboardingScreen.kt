package com.example.ppab_responsi1_kelompok09.presentation.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.presentation.onboard.components.OnboardingButton
import com.example.ppab_responsi1_kelompok09.presentation.onboard.components.OnboardingIndicator
import com.example.ppab_responsi1_kelompok09.presentation.onboard.components.OnboardingPage
import com.example.ppab_responsi1_kelompok09.presentation.onboard.components.OnboardingTextButton
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen (navController: NavController) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        val pagerState = rememberPagerState (initialPage = 0) {
            OnboardList.size
        }

        val buttonState = remember {
            derivedStateOf {
                when(pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }

        HorizontalPager(state = pagerState) { i ->
            OnboardingPage(pages = OnboardList[i])
        }

        OnboardingIndicator(
            pageSize = OnboardList.size,
            selectedPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -(144.dp))
        )

        val arrangement = if (buttonState.value[0].isNotEmpty()) {
                Arrangement.spacedBy(16.dp)
            } else { Arrangement.Center }


        Row (
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
                .offset(y = -(68.dp)),
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.Bottom
        ) {
            val scope = rememberCoroutineScope()
            val density = LocalDensity.current

            if (buttonState.value[0].isNotEmpty()) {
                OnboardingTextButton(
                    text = buttonState.value[0],
                    onClick = {
                        val targetPage = pagerState.currentPage - 1
                        val pageOffset = targetPage - pagerState.currentPage

                        scope.launch {
                            val pageWidth = 360.dp
                            val distanceDp = pageWidth * pageOffset
                            val distancePx = with(density) { distanceDp.toPx() }

                            val steps = 10
                            val stepSize = distancePx / steps

                            repeat(steps) {
                                pagerState.scrollBy(stepSize)
                                delay(30)
                            }

                            pagerState.scrollToPage(targetPage)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            OnboardingButton(
                text = buttonState.value[1],
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == 2) {
                            navController.navigate("splash") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        } else {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}