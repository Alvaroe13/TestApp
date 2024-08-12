package com.ai.notelist.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
fun DeletionDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {

    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = com.ai.notelist.R.string.deletion_title)) },
            text = { Text(text = stringResource(id = com.ai.notelist.R.string.deletion_description)) },
            onDismissRequest = {
                onCancel()
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onCancel()
                    }
                ) {
                    Text(stringResource(id = com.ai.notelist.R.string.cancel))
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onConfirm()
                    }
                ) {
                    Text(stringResource(id = com.ai.notelist.R.string.confirm))
                }
            }
        )
    }
}