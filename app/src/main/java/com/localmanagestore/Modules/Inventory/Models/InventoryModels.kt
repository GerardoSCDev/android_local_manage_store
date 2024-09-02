package com.localmanagestore.Modules.Inventory.Models

data class ListProductsItemData(
    val name: String,
    val amount: Int,
    var barCode: String
)