package com.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.localmanagestore.R

@Composable
fun ListProductBoxImage(uri: String = "") {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
    ) {
        if (uri == "") {
            Image(
                painter = painterResource(id = R.drawable.productos_default_image),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(5.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }


    }
}