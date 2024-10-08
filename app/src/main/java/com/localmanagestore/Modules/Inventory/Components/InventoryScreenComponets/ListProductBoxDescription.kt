package com.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity

@Composable
fun ListProductBoxDescription(product: ProductEntity) {
    Column(
        modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
    ) {
        Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Text(text = "Cantidad: ${product.stock}")
        Text(text = "Código: ${product.barcode}")
        Text(text = "Descripción: ${product.detail}")
    }
}