package com.localmanagestore.CommonUtils.AppComponents.AppButton

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class AppButtonType {
    Success, Denie, WhiteButton
}

@Composable
fun AppButton(
    type: AppButtonType,
    title: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    ElevatedButton(
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = getButtonColors(type),
        onClick = onClick
    ) {
        Text(title)
    }
}

@Composable
fun getButtonColors(type: AppButtonType) : ButtonColors {
    when (type) {
        AppButtonType.Success -> {
            return ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF032030),
                disabledContainerColor = Color(0xFFBBBBBB),
                disabledContentColor = Color.Black
            )
        }
        AppButtonType.Denie -> {
            return ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF636B6F),
                disabledContainerColor = Color(0xFFBBBBBB),
                disabledContentColor = Color.Black
            )
        }
        AppButtonType.WhiteButton -> {
            return ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color(0xFFFAFAFA),
                disabledContainerColor = Color(0xFFBBBBBB),
                disabledContentColor = Color.Black
            )
        }
        else -> return ButtonDefaults.buttonColors()
    }
}