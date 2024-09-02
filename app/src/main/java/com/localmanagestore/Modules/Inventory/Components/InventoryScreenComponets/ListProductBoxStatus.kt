package com.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListProductBoxStatus(amount: Int) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(13.dp)
            .background(getColorStatus(amount))
    )
}

fun getColorStatus(amount: Int) : Color {
    return if (amount >= 10) {
        Color.Green
    } else if (amount in 1..9) {
        Color.Yellow
    } else {
        Color.Red
    }
}