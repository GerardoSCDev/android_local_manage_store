package com.example.localmanagestore.Modules.Inventory.Models

data class InventoryBottomSheetAddProductFormData (
    val barcode: String? = "",
    val name: String? = "",
    val stock: Int? = 0,
    val detail: String? = "",
)
