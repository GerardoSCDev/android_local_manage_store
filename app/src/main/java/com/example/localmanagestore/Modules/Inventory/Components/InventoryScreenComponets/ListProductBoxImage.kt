package com.example.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.localmanagestore.R

@Composable
fun ListProductBoxImage() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.productos_default_image),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(5.dp)
        )
    }
}