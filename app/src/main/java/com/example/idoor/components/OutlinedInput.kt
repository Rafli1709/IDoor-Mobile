package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.R
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun OutlinedInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    cornerRadius: Dp = 16.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    clickableTrailingIcon: Boolean = false,
    onTrailingIconClick: () -> Unit = {},
    unfocusedBorderColor: Color = LightGray,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        label = if (label != null) {
            {
                Text(
                    text = label,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            }
        } else {
            null
        },
        placeholder = if (placeholder != null) {
            {
                Text(
                    text = placeholder,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            }
        } else {
            null
        },
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = stringResource(id = R.string.input_email),
                )
            }
        } else {
            null
        },
        trailingIcon = if (trailingIcon != null) {
            {
                val trailingIconModifier =
                    if (clickableTrailingIcon) Modifier.clickable { onTrailingIconClick() } else Modifier
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .then(trailingIconModifier)
                )
            }
        } else {
            null
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = RoundedCornerShape(cornerRadius),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = LightGray,
            focusedContainerColor = LightGray,
            errorContainerColor = LightGray,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedBorderColor = SteelBlue,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = LightGray,
            errorLabelColor = LightGray,
            unfocusedLeadingIconColor = Color.Gray,
            focusedLeadingIconColor = SteelBlue,
            errorLeadingIconColor = Color.Red,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            errorSupportingTextColor = LightGray
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun OutlinedInputPreview() {
    var name by remember { mutableStateOf("") }
    var visualTransformation by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }
    var trailingIcon by remember { mutableStateOf(Icons.Default.Visibility) }
    Box(
        modifier = Modifier
            .background(MidnightBlue)
            .padding(16.dp)
    ) {
        OutlinedInput(
            value = name,
            label = "Password",
            placeholder = "Password",
            visualTransformation = visualTransformation,
            onValueChange = {
                name = it
            },
            leadingIcon = Icons.Default.Password,
            trailingIcon = trailingIcon,
            clickableTrailingIcon = true,
            onTrailingIconClick = {
                if (trailingIcon == Icons.Default.Visibility) {
                    visualTransformation = VisualTransformation.None
                    trailingIcon = Icons.Default.VisibilityOff
                } else {
                    visualTransformation = PasswordVisualTransformation()
                    trailingIcon = Icons.Default.Visibility
                }
            }
        )
    }
}