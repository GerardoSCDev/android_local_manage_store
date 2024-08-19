package com.example.localmanagestore.Modules.StartApp.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.localmanagestore.Modules.Inventory.Screens.InventoryScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartAppScreen () {
    val pagerState = rememberPagerState(pageCount = {3})
    HorizontalPager(
        state = pagerState
    ) { page ->
        when (page) {
            0 -> WelcomeScreen(pagerState)
            1 -> PrivacyPolicyScreen(pagerState)
            2 -> {
                Scaffold (
//                bottomBar = { AppButtonNavBar() }
                )
                {
                    InventoryScreen()
                }
            }
            else -> {}
        }
    }
}