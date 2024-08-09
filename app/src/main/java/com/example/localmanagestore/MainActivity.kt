package com.example.localmanagestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.localmanagestore.CommonUtils.AppComponents.AppButtonNavBar.AppButtonNavBar
import com.example.localmanagestore.Modules.Inventory.Screens.InventoryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            Scaffold (
//                bottomBar = { AppButtonNavBar() }
            )
            {
                InventoryScreen()
            }
        }
    }
}
