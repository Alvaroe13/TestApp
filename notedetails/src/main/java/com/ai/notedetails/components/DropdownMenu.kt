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
import com.ai.common_domain.entities.Computer
import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.Keyboard
import com.ai.common_domain.entities.NoteObject
import com.ai.common_domain.entities.Server

@Composable
fun Dropdown(
    onDismiss: () -> Unit,
    onOptionSelected: (option: NoteObject) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(true) }

    if (expanded) {
        DropdownMenu(
            modifier = Modifier
                .wrapContentHeight()
            ,
            expanded = expanded ,
            onDismissRequest = {
                expanded = false
                onDismiss()
            }
        ) {

            option().forEach {
                DropdownMenuItem(
                    text = {  Text(text = it.name) },
                    onClick = {
                        onOptionSelected(it)
                        onDismiss()
                    }
                )
            }
        }
    }
}

private fun option() : Set<NoteObject> {
    return buildSet {
        add(Desk())
        add(Human())
        add(Server())
        add(Keyboard())
        add(Computer())
    }
}