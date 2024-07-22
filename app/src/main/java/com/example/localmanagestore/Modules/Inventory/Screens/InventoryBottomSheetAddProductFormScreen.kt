package com.example.localmanagestore.Modules.Inventory.Screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun InventoryBottomSheetAddProductForm(onDismiss: () -> Unit) {

    val viewModel = InventoryBottomSheetAddProductFormViewModel(context = LocalContext.current)

    val (barcodeValue, onBarcodeValueChange) = rememberSaveable { mutableStateOf("") }
    val (barcodeValueHelper, onBarcodeValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (barcodeValueIsError, onBarcodeValueIsErrorChange) = rememberSaveable { mutableStateOf(true) }

    val (nameValue, onNameValueChange) = rememberSaveable { mutableStateOf("") }
    val (nameValueHelper, onNameValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (nameValueIsError, onNameValueIsErrorChange) = rememberSaveable { mutableStateOf(true) }

    val (amountValue, onAmountValueChange) = rememberSaveable { mutableStateOf("") }
    val (amountValueHelper, onAmountValueHelperChange) = rememberSaveable { mutableStateOf("") }
    val (amountValueIsError, onAmountValueIsErrorChange) = rememberSaveable { mutableStateOf(true) }

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

    if (isValidatePermissionState) {
        isValidatePermissionState = false
        ShowPermission(cameraPermissionState = cameraPermissionState)
    }

    AutoDismissDialog(
        showDialog = shouldShowDialog.value,
        message = "Producto almacenado de forma correcta",
        onDismiss = {
            shouldShowDialog.value = false
            onDismiss()
        }
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.height(420.dp),
    ) {

        Text(
            text = "Nuevo Producto",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold)
        )

        AppTextField(
            textValue = barcodeValue,
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
            textValue = nameValue,
            onTextValueChange = onNameValueChange,
            label = "Nombre",
            rules = listOf(AppTextFieldRules.NOEMPTY),
            onIsErrorChange = onNameValueIsErrorChange,
            isError = nameValueIsError,
            textHelper = nameValueHelper,
            onTextHelperChange = onNameValueHelperChange,
        )

        AppTextField(
            textValue = amountValue,
            onTextValueChange = onAmountValueChange,
            label = "Monto",
            rules = listOf(AppTextFieldRules.NOEMPTY),
            keyboardType = KeyboardType.Number,
            isError = amountValueIsError,
            onIsErrorChange = onAmountValueIsErrorChange,
            textHelper = amountValueHelper,
            onTextHelperChange = onAmountValueHelperChange,
        )

        AppButton(
            title = "Guardar",
            enabled = !(barcodeValueIsError || nameValueIsError || amountValueIsError),
            onClick = {
                viewModel.setFormDataValues(barcodeValue, nameValue, amountValue.toInt())
                viewModel.updateStorage {success: Boolean ->
                    shouldShowDialog.value = success
                }
            }
        )

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
