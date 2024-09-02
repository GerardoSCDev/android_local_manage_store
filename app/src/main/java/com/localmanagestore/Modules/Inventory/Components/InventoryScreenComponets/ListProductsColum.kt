package com.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity

@Composable
fun ListProductsColum(items: List<ProductEntity>, paddingValues: PaddingValues, showBottomSheet: MutableState<Boolean>, isUpdateForm: MutableState<Boolean>, productUpdated:  MutableState<ProductEntity>) {
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
                    .height(110.dp)
            ) {
                Row (
                    modifier = Modifier.clickable {
                        productUpdated.value = item
                        isUpdateForm.value = true
                        showBottomSheet.value = true
                    }
                ) {
                    ListProductBoxStatus(item.stock)
                    ListProductBoxImage(uri = item.photoPath)
                    ListProductBoxDescription(item)
                }
            }
        }
    }
}