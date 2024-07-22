package com.example.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.example.localmanagestore.CommonUtils.AppComponents.AppButtonNavBar.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductTopBar(showBottomSheet: MutableState<Boolean>) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF032030),
        ),
        title = { Text(text = AppScreens.Inventory.name, color = Color.White) },
        actions = {
            IconButton(onClick = {
                showBottomSheet.value = !showBottomSheet.value
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
            }
        }
    )
}