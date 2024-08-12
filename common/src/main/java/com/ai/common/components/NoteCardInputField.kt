package com.ai.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ai.common.theme.TestAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCardInputField(
    modifier: Modifier = Modifier,
    header: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
            ,
            label = {
                Text(
                    text = header,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputFieldPreview(){
    TestAppTheme {
        NoteCardInputField(
            header = "Type",
            value ="Title"
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputFieldNoHeaderPreview(){
    TestAppTheme {
        NoteCardInputField(
            header = "",
            value = "Description",
        ) {}
    }
}