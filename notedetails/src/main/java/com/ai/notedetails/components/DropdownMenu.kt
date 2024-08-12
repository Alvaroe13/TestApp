package com.ai.notedetails.components

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun Dropdown(
    onDismiss: () -> Unit
) {
    var display by remember { mutableStateOf(true) }

    if (display) {
        DropdownMenu(
            modifier = Modifier
                .wrapContentHeight()
            ,
            expanded = display ,
            onDismissRequest = {
                display = false
                onDismiss()
            }
        ) {
            DropdownMenuItem(
                text = {  Text(text = "something") },
                onClick = { }
            )
        }
    }
}