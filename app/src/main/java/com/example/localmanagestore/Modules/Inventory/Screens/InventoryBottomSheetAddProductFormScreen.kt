package com.example.localmanagestore.Modules.Inventory.Screens

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localmanagestore.CommonUtils.AppComponents.AppButton.AppButton
import com.example.localmanagestore.CommonUtils.AppComponents.AppStatusDialog.AutoDismissDialog
import com.example.localmanagestore.CommonUtils.AppComponents.AppTextField.AppTextField
import com.example.localmanagestore.CommonUtils.AppComponents.AppTextField.AppTextFieldHelpers
import com.example.localmanagestore.CommonUtils.AppComponents.AppTextField.AppTextFieldRules
import com.example.localmanagestore.Modules.Inventory.ViewModels.InventoryBottomSheetAddProductFormViewModel
import com.example.localmanagestore.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.localmanagestore.CommonUtils.AppComponents.AppButton.AppButtonType
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun InventoryBottomSheetAddProductForm(isUpdateForm: MutableState<Boolean>, product: ProductEntity? = null, onDismiss: () -> Unit) {

    val currentContext = LocalContext.current
    val viewModel = InventoryBottomSheetAddProductFormViewModel(context = LocalContext.current)

    val (barcodeValue, onBarcodeValueChange) = rememberSaveable { mutableStateOf(product?.barcode) }
    val (barcodeValueHelper, onBarcodeValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (barcodeValueIsError, onBarcodeValueIsErrorChange) = rememberSaveable { mutableStateOf(product?.barcode == "") }

    val (nameValue, onNameValueChange) = rememberSaveable { mutableStateOf(product?.name) }
    val (nameValueHelper, onNameValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (nameValueIsError, onNameValueIsErrorChange) = rememberSaveable { mutableStateOf(product?.name == "") }

    val (stockValue, onStockValueChange) = rememberSaveable { mutableStateOf(product?.stock.toString()) }
    val (stockValueHelper, onStockValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (stockValueIsError, onStockValueIsErrorChange) = rememberSaveable { mutableStateOf(product?.stock.toString() == "") }

    val (detailValue, onDetailValueChange) = rememberSaveable { mutableStateOf(product?.detail) }
    val (detailValueHelper, onDetailValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (detailValueIsError, onDetailValueIsErrorChange) = rememberSaveable { mutableStateOf(product?.detail == "") }

    var photoPath: String = ""

    val isSuccessForm = remember { mutableStateOf(false) }

    val shouldShowDialog = remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState()
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var isValidatePermissionState by remember { mutableStateOf(false) }
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            val resultScan = result.contents?:"Sin resultado"
            val appTextFieldHelpers = AppTextFieldHelpers()
            val resultValidateRules = appTextFieldHelpers.validateRules(listOf(AppTextFieldRules.NOEMPTY), resultScan)
            onBarcodeValueChange(resultScan)
            onBarcodeValueHelperChange(resultValidateRules.errorMsg)
            onBarcodeValueIsErrorChange(!resultValidateRules.isValidate)
        }
    )
    val bitmapProduct = remember { mutableStateOf<Bitmap?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        bitmapProduct.value = it
    }

    if (isValidatePermissionState) {
        isValidatePermissionState = false
        ShowPermission(cameraPermissionState = cameraPermissionState)
    }

    AutoDismissDialog(
        isError = !isSuccessForm.value,
        showDialog = shouldShowDialog.value,
        message = if (isSuccessForm.value) "Producto almacenado de forma correcta" else "No fue posible almacenar el producto",
        onDismiss = {
            shouldShowDialog.value = false
            onDismiss()
        }
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.height(620.dp),
    ) {

        Text(
            text = if (isUpdateForm.value) "Actualizar Producto" else "Nuevo Producto",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold)
        )

        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        ) {

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(
                    3.dp,
                    Color(0xFF032030)
                ),
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    bitmapProduct.value?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .align(Alignment.Center)
                        )
                    }
                    if (bitmapProduct.value == null) {
                        product?.photoPath?.let {
                            Image(
                                painter = rememberAsyncImagePainter(model = it),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .align(Alignment.Center)
                            )
                        } ?: run {
                            Image(
                                painter = painterResource(id = R.drawable.productos_default_image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFF032030),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(30.dp),
                        onClick = {
                            if (cameraPermissionState.status.isGranted) {

                                cameraLauncher.launch()

                            } else {
                                isValidatePermissionState = true
                                cameraPermissionState.run { launchPermissionRequest() }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = ""
                        )
                    }
                }
            }

        }

        AppTextField(
            textValue = barcodeValue ?: "",
            onTextValueChange = onBarcodeValueChange,
            textHelper = barcodeValueHelper,
            onTextHelperChange = onBarcodeValueHelperChange,
            isError = barcodeValueIsError,
            onIsErrorChange = onBarcodeValueIsErrorChange,
            rules = listOf(AppTextFieldRules.NOEMPTY),
            label = "Código de barra",
            trailingIcon = R.drawable.rounded_barcode_scanner_24,
            keyboardType = KeyboardType.Number,
            onClick = {
                if (cameraPermissionState.status.isGranted) {
                    val scanOptions = ScanOptions()
                    scanOptions.setPrompt("Colca en el centro el código de barras")
                    scanOptions.setOrientationLocked(true)
                    scanOptions.setTorchEnabled(true)
                    scanLauncher.launch(scanOptions)
                } else {
                    isValidatePermissionState = true
                    cameraPermissionState.run { launchPermissionRequest() }
                }
            }
        )

        AppTextField(
            textValue = nameValue ?: "",
            onTextValueChange = onNameValueChange,
            label = "Nombre",
            rules = listOf(AppTextFieldRules.NOEMPTY),
            onIsErrorChange = onNameValueIsErrorChange,
            isError = nameValueIsError,
            textHelper = nameValueHelper,
            onTextHelperChange = onNameValueHelperChange,
        )

        AppTextField(
            textValue = stockValue,
            onTextValueChange = onStockValueChange,
            label = "Cantidad",
            rules = listOf(AppTextFieldRules.NOEMPTY),
            keyboardType = KeyboardType.Number,
            isError = stockValueIsError,
            onIsErrorChange = onStockValueIsErrorChange,
            textHelper = stockValueHelper,
            onTextHelperChange = onStockValueHelperChange,
        )

        AppTextField(
            textValue = detailValue ?: "",
            onTextValueChange = onDetailValueChange,
            label = "Detalle",
            rules = listOf(AppTextFieldRules.NOEMPTY),
            isError = detailValueIsError,
            onIsErrorChange = onDetailValueIsErrorChange,
            textHelper = detailValueHelper,
            onTextHelperChange = onDetailValueHelperChange,
        )

        if (isUpdateForm.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .weight(1f)
                ) {
                    AppButton(
                        type = AppButtonType.Success,
                        title = "Actualizar",
                        onClick = {}
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .weight(1f)
                ) {
                    AppButton(
                        type = AppButtonType.Denie,
                        title = "Borrar",
                        onClick = {}
                    )
                }
            }
        } else {
            AppButton(
                type = AppButtonType.Success,
                title = "Guardar",
                enabled = !(barcodeValueIsError || nameValueIsError || stockValueIsError || detailValueIsError),
                onClick = {
                    val uriPhotoProduct = getUriPhotoProduct(currentContext)
                    photoPath = uriPhotoProduct.toString()
                    viewModel.setFormDataValues(barcodeValue ?: "", nameValue ?: "", stockValue.toInt(), detailValue ?: "", photoPath)
                    viewModel.updateStorage {success: Boolean ->
                        if (success) {
                            bitmapProduct.value?.let { saveImage(it, currentContext, uriPhotoProduct) }
                        }
                        isSuccessForm.value = success
                        shouldShowDialog.value = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowPermission(cameraPermissionState: PermissionState) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted
        } else {
            // Handle permission denial
        }
    }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
            // Show rationale if needed
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

private fun getUriPhotoProduct(context: Context): Uri? {
    val values = contentValues()
    values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "ProductsImage")
    values.put(MediaStore.Images.Media.IS_PENDING, true)
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
}
/// @param folderName can be your app's name
private fun saveImage(bitmap: Bitmap, context: Context, uri: Uri?) {
    if (android.os.Build.VERSION.SDK_INT >= 29) {
        val values = contentValues()

        if (uri != null) {
            saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
            values.put(MediaStore.Images.Media.IS_PENDING, false)
            context.contentResolver.update(uri, values, null, null)
        }
    } else {
        val directory = File(Environment.getExternalStorageDirectory().toString() + separator + "ProductsImage")
        // getExternalStorageDirectory is deprecated in API 29

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + ".png"
        val file = File(directory, fileName)
        saveImageToStream(bitmap, FileOutputStream(file))
        if (file.absolutePath != null) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
    }
}

private fun contentValues() : ContentValues {
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
    return values
}

private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
    if (outputStream != null) {
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}