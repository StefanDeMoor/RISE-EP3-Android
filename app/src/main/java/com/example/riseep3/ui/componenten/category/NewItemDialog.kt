package com.example.riseep3.ui.componenten.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.riseep3.R
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun NewItemDialog(
    itemName: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onPrimary,
            tonalElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Create Overview",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = Montagu,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.5.sp
                        )
                    )
                }

                TextField(
                    value = itemName,
                    onValueChange = onNameChange,
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedDialogButton(
                        text = "Cancel",
                        onClick = onDismiss,
                        color = colorResource(R.color.dark_gray),
                        textColor = Color.White,
                        borderColor = Color.White
                    )
                    AnimatedDialogButton(
                        text = "Create",
                        onClick = onConfirm,
                        color = colorResource(R.color.light_screen_add),
                        textColor = Color.Black,
                        borderColor = Color.White
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun NewItemDialogPreview() {
    var name by remember { mutableStateOf("Preview Item") }
    RiseTheme {
        NewItemDialog(
            itemName = name,
            onNameChange = { name = it },
            onConfirm = {},
            onDismiss = {}
        )
    }
}