package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue


data class DropdownOption(
    val value: Int,
    val text: String
)

@Composable
fun DropdownInput(
    query: String,
    isLoading: Boolean,
    errorMessage: String,
    placeholder: String,
    onRetry: () -> Unit,
    onSearch: (String) -> Unit,
    onValueChange: (Int) -> Unit,
    lists: List<DropdownOption>,
    selectedItem: String = "",
    label: String = "Label",
    leadingIcon: ImageVector? = null,
) {
    var showDropdown by remember { mutableStateOf(false) }
    var displayText by remember { mutableStateOf("") }
    var size by remember { mutableStateOf(Size.Zero) }
    val icon by remember {
        mutableStateOf(if (showDropdown) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp)
    }

    LaunchedEffect(selectedItem){
        displayText = selectedItem
    }

    Column {
        Box {
            OutlinedInput(
                value = displayText,
                onValueChange = {},
                readOnly = true,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = icon,
                clickableTrailingIcon = true,
                onTrailingIconClick = {
                    showDropdown = !showDropdown
                },
                modifier = Modifier
                    .onGloballyPositioned {
                        size = it.size.toSize()
                    }
            )

            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = {
                    showDropdown = false
                },
                offset = DpOffset(x = 0.dp, y = 5.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(with(LocalDensity.current) { size.width.toDp() })
                    .heightIn(max = 300.dp)

            ) {
                Column {
                    OutlinedInput(
                        value = query,
                        onValueChange = onSearch,
                        placeholder = placeholder,
                        cornerRadius = 40.dp,
                        unfocusedBorderColor = MidnightBlue,
                        leadingIcon = Icons.Default.Search,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = LightGray,
                            modifier = Modifier.size(80.dp)
                        )
                    } else if (errorMessage.isNotEmpty()) {
                        RetrySection(
                            error = errorMessage,
                            onRetry = onRetry
                        )
                    } else {
                        lists.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it.text,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                    )
                                },
                                onClick = {
                                    onValueChange(it.value)
                                    displayText = it.text
                                    showDropdown = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun DropdownInput2Preview() {
    var selectedValue by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val dropdownItems = (1..5).map {
        DropdownOption(
            value = it,
            text = "Option $it"
        )
    }

    Box(
        modifier = Modifier
            .background(MidnightBlue)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        DropdownInput(
            query = searchQuery,
            placeholder = "Pilih user...",
            isLoading = false,
            errorMessage = "",
            onRetry = {},
            onSearch = { newQuery -> searchQuery = newQuery },
            onValueChange = { newValue -> selectedValue = newValue },
            lists = dropdownItems
        )
    }
}