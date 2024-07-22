package com.example.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity
import com.example.localmanagestore.Modules.Inventory.Models.ListProductsItemData

@Composable
fun ListProductsColum(items: List<ProductEntity>, paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(
                bottom = paddingValues.calculateBottomPadding() + 15.dp,
                top = paddingValues.calculateTopPadding() + 15.dp,
                start = 15.dp,
                end = 15.dp
            )
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items.forEach { item ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 15.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Row {
                    ListProductBoxStatus(10)
                    ListProductBoxImage()
                    ListProductBoxDescription(item)
                }
            }
        }
    }
}