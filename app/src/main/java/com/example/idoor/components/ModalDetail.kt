package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.idoor.ui.theme.DarkRed
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun ModalDetail(
    title: String,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    isUpdate: Boolean = true,
    onUpdate: () -> Unit = {},
    anotherButton: @Composable () -> Unit = {},
    status: String = "Master"
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),

        ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(15.dp))
                .border(1.dp, LightGray, shape = RoundedCornerShape(15.dp))
                .background(MidnightBlue)
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = LightGray,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = LightGray,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onDismiss()
                        }
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 15.dp),
                color = LightGray,
                thickness = 0.4.dp
            )
            content()
            if (status == "Master") {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    color = LightGray,
                    thickness = 0.4.dp
                )
                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    anotherButton()
                    if (isUpdate) {
                        ButtonIcon(
                            icon = Icons.Default.Edit,
                            onClick = onUpdate,
                            background = SteelBlue,
                            iconSize = 16.dp,
                            containerSize = 30.dp
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                    }
                    ButtonIcon(
                        icon = Icons.Default.Delete,
                        onClick = onDelete,
                        background = DarkRed,
                        iconSize = 16.dp,
                        containerSize = 30.dp
                    )
                }
            }
        }
    }
}