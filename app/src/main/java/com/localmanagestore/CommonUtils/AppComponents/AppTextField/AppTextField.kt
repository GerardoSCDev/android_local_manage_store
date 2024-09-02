package com.localmanagestore.CommonUtils.AppComponents.AppTextField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    label: String = "",
    textValue: String,
    textHelper: String = "",
    isError: Boolean = false,
    isPassword: Boolean = false,
    trailingIcon: Int = 0,
    keyboardType: KeyboardType = KeyboardType.Text,
    rules: List<AppTextFieldRules> = emptyList(),
    onClick: () -> Unit = {},
    onTextValueChange: (String) -> Unit = {},
    onTextHelperChange: (String) -> Unit = {},
    onIsErrorChange: (Boolean) -> Unit = {}
) {

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
        value = textValue,
        onValueChange = { newValue ->
            onTextValueChange(newValue)

            onTextHelperChange("")
            onIsErrorChange(false)

            val appTextFieldHelpers = AppTextFieldHelpers()
            val resultValidateRules = appTextFieldHelpers.validateRules(rules, newValue)

            onTextHelperChange(resultValidateRules.errorMsg)
            onIsErrorChange(!resultValidateRules.isValidate)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        supportingText = {
            Text(
                text = textHelper,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.error
            )
        },
        trailingIcon = {
            if (trailingIcon != 0) {
                IconButton(
                    onClick = onClick
                ) {
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = ""
                    )
                }
            }
        },
    )
}

class AppTextFieldHelpers {
    data class ValidateRulesResult (val errorMsg: String, val isValidate: Boolean)

    fun validateRules(rules: List<AppTextFieldRules>, text: String) : ValidateRulesResult {
        rules.forEach { rule ->
            when (rule) {
                AppTextFieldRules.NOEMPTY -> {
                    if (text.isEmpty()) {
                        return ValidateRulesResult("Ingresa un valor", isValidate = false)
                    }
                }
                AppTextFieldRules.MAXCHARACTERS -> {
                    if (text.length == 10) {
                        return ValidateRulesResult("No puede revasar el maximo de caracteres", isValidate = false)
                    }
                }
                else -> {
                    return ValidateRulesResult("", isValidate = true)
                }
            }
        }
        return ValidateRulesResult("", isValidate = true)
    }
}

enum class AppTextFieldRules {
    NOEMPTY, MAXCHARACTERS, NOSPECIALCHARACTERS
}