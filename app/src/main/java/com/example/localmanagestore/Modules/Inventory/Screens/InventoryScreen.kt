package com.example.localmanagestore.Modules.Inventory.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity
import com.example.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets.ListProductTopBar
import com.example.localmanagestore.Modules.Inventory.Components.InventoryScreenComponets.ListProductsColum
import com.example.localmanagestore.Modules.Inventory.Models.ListProductsItemData
import com.example.localmanagestore.Modules.Inventory.ViewModels.InventoryBottomSheetAddProductFormViewModel
import com.example.localmanagestore.R

@Composable
fun InventoryScreen() {
    val showBottomSheet = remember { mutableStateOf(false) }
    val viewModel = InventoryBottomSheetAddProductFormViewModel(context = LocalContext.current)
    val productsList = remember { mutableStateListOf<ProductEntity>() }

    viewModel.getAllStorage { _, products ->
        productsList -= productsList
        productsList += products
    }

    Scaffold(
        topBar = { ListProductTopBar(showBottomSheet = showBottomSheet) }
    ) { paddingValues ->


        if (showBottomSheet.value) {
            InventoryBottomSheetAddProductForm {
                showBottomSheet.value = false
                viewModel.getAllStorage { _, products ->
                    productsList -= productsList
                    productsList += products
                }
            }
        }

        if (productsList.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.productos_default_image),
                        contentDescription = stringResource(id = R.string.app_name),
                    )
                    Text(text = "Agrega un nuevo producto")
                }

            }

        } else {
            ListProductsColum(items = productsList, paddingValues = paddingValues)
        }


    }
}
