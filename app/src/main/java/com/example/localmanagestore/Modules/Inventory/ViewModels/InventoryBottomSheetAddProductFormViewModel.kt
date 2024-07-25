package com.example.localmanagestore.Modules.Inventory.ViewModels

import android.content.Context
import com.example.localmanagestore.CommonUtils.RoomDB.DB.AppDatabase
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity
import com.example.localmanagestore.CommonUtils.Utils.Strings.UtilsStrings
import com.example.localmanagestore.Modules.Inventory.Models.InventoryBottomSheetAddProductFormData

class InventoryBottomSheetAddProductFormViewModel(private val context: Context) {
    
    private val utilsString = UtilsStrings()
    private val database = AppDatabase.getDatabase(context)
    private val productDAO = database.productDao()

    private var formData: InventoryBottomSheetAddProductFormData? = null

    init {

    }

    fun setFormDataValues(barcode: String, name: String,  amount: Int, detail: String) {
        formData = InventoryBottomSheetAddProductFormData(barcode, name, amount, detail)
    }

    fun getAllStorage(callback: (success: Boolean, products: List<ProductEntity>) -> Unit) {

        var resProducts: List<ProductEntity> = emptyList()

        try {

            resProducts = productDAO.getAllNotes()

            callback(true, resProducts)
        } catch (e: Exception) {

            callback(false, resProducts)


        }
    }

    fun updateStorage(callback: (success: Boolean) -> Unit) {
        try {

            val form = formData ?: return
            val barcode = form.barcode ?: return
            val name = form.name ?: return
            val stock = form.stock ?: return
            val detail = form.detail ?: return
            val today = utilsString.getDateCurrentToString()

            val productEntity = ProductEntity(barcode = barcode, name = name, stock = stock, detail = detail, photo = "", updateAt = today, insertAt = today)
            productDAO.insert(productEntity)

            callback(true)
        } catch (e: Exception) {
            callback(false)
        }

    }
}