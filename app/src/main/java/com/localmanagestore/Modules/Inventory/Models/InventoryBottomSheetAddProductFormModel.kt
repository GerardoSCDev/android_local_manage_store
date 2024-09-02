package com.localmanagestore.Modules.Inventory.Models

data class InventoryBottomSheetAddProductFormData (
    val id: Int? = 0,
    val barcode: String? = "",
    val name: String? = "",
    val stock: Int? = 0,
    val detail: String? = "",
    val photoPath: String? = "",
    val insertAt: String? = ""
)
