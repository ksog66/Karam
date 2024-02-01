package com.kklabs.karam.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun TaskLogInput(
    modifier: Modifier = Modifier,
    sendTasklog: (message: String) -> Unit
) {
    var message by remember { mutableStateOf("") }

    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { TextP50("Type your message...") },
            modifier = Modifier
                .weight(1f),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (message.isNotEmpty()) {
                        sendTasklog(message.trim())
                        message = ""
                    }
                }
            ),
            singleLine = true,
        )

        IconButton(
            onClick = {
                if (message.isNotEmpty()) {
                    sendTasklog(message.trim())
                    message = ""
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
            )
        }
    }
}