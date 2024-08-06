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

    fun setFormDataValues(barcode: String, name: String,  amount: Int, detail: String, photoPath: String, insertAt: String = "", id: Int = 0) {
        formData = InventoryBottomSheetAddProductFormData(id,barcode, name, amount, detail, photoPath, insertAt )
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

    fun updateProduct(callback: (success: Boolean) -> Unit) {
        try {
            val form = formData ?: return
            val id = form.id ?: return
            val barcode = form.barcode ?: return
            val name = form.name ?: return
            val stock = form.stock ?: return
            val detail = form.detail ?: return
            val photoPath = form.photoPath ?: return
            val insertAt = form.insertAt ?: return
            val today = utilsString.getDateCurrentToString()

            val productEntity = ProductEntity(id = id, barcode = barcode, name = name, stock = stock, detail = detail, photoPath = photoPath, updateAt = today, insertAt = insertAt)
            productDAO.update(productEntity)

            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }
    fun insertProduct(callback: (success: Boolean) -> Unit) {
        try {
            val form = formData ?: return
            val barcode = form.barcode ?: return
            val name = form.name ?: return
            val stock = form.stock ?: return
            val detail = form.detail ?: return
            val photoPath = form.photoPath ?: return
            val today = utilsString.getDateCurrentToString()

            val productEntity = ProductEntity(barcode = barcode, name = name, stock = stock, detail = detail, photoPath = photoPath, updateAt = today, insertAt = today)
            productDAO.insert(productEntity)

            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

    fun deleteProduct( product: ProductEntity, callback: (success: Boolean) -> Unit) {
        try {
            productDAO.delete(product)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }
}