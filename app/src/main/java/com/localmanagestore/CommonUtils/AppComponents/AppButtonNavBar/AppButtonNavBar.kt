package com.localmanagestore.CommonUtils.AppComponents.AppButtonNavBar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.localmanagestore.Modules.Inventory.Screens.InventoryScreen
import com.localmanagestore.Modules.Sales.Screens.SalesScreen

enum class AppScreens { Inventory, Sales, Statistic }

data class AppNavItem(
    val title: String,
    val icon: ImageVector,
    val router: String
)

val listAppNavItems: List<AppNavItem> = listOf(
    AppNavItem(title = "Inventario", icon = Icons.Default.Edit, router = AppScreens.Inventory.name),
    AppNavItem(title = "Ventas", icon = Icons.Default.ShoppingCart, router = AppScreens.Sales.name),
    AppNavItem(title = "Estadistica", icon = Icons.Default.Info, router = AppScreens.Statistic.name),
)

@Composable
fun AppButtonNavBar() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF032030),
                modifier = Modifier
                    .height(70.dp)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    },
            ) {
                val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
                val currentDestination: NavDestination? = navBackStackEntry?.destination

                listAppNavItems.forEach { appNavItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == appNavItem.router } == true,
                        onClick = {
                            navController.navigate(appNavItem.router) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = appNavItem.icon, contentDescription = null) },
                        label = { Text(text = appNavItem.title, color = Color.White) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White,
                            indicatorColor = Color(0xFF00314D),
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Inventory.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = AppScreens.Inventory.name) { InventoryScreen() }
            composable(route = AppScreens.Sales.name) { SalesScreen() }
            composable(route = AppScreens.Statistic.name) { SalesScreen() }
        }
    }
}