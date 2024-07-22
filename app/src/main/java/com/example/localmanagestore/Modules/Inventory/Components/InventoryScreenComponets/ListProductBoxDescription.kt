package com.example.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity
import com.example.localmanagestore.Modules.Inventory.Models.ListProductsItemData

@Composable
fun ListProductBoxDescription(product: ProductEntity) {
    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Text(text = "Cantidad: ${10}")
        Text(text = "Código: ${product.barcode}")
    }
}