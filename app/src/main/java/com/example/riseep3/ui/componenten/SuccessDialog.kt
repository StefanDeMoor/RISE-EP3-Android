package com.example.riseep3.ui.componenten

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.riseep3.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            delay(1200)
            onDismiss()
        }
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        title = {},
        containerColor = colorResource(R.color.light_screen_add),
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = colorResource(R.color.light_screen_add),
                    modifier = Modifier.size(80.dp),
                    tonalElevation = 8.dp,
                    shadowElevation = 8.dp,
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.check),
                            colorFilter = ColorFilter.tint(Color.White),
                            contentDescription = "Success",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SuccessDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        SuccessDialog(onDismiss = { showDialog = false })
    }
}
