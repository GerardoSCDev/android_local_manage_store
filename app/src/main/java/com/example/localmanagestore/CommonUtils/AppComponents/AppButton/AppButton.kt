package com.example.localmanagestore.CommonUtils.AppComponents.AppButton

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    title: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    ElevatedButton(
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 10.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF032030)
        ),
        onClick = onClick
    ) {
        Text(title)
    }
}