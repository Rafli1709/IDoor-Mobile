package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.R
import com.example.idoor.ui.theme.MidnightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    placeholder: String? = null,
    shape: Shape = RoundedCornerShape(10.dp),
    height: Dp = 48.dp
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.ic_search)
            )
        },
        placeholder = {
            if (placeholder != null) {
                Text(
                    text = placeholder,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            }
        },
        shape = shape,
        modifier = modifier
            .heightIn(min = height)
    ) {

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun SearchInputPreview() {
    Box (
        modifier = Modifier
            .background(MidnightBlue)
            .padding(16.dp)
    ) {
        SearchInput(
            query = "Tes",
            onQueryChange = {  },
            onSearch = {  },
            active = true,
            onActiveChange = {  },
            height = 20.dp
        )
    }
}