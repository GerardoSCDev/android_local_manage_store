package com.example.localmanagestore.Modules.Inventory.Models

data class InventoryBottomSheetAddProductFormData (
    val barcode: String? = "",
    val name: String? = "",
    val amount: Int? = 0,
)
