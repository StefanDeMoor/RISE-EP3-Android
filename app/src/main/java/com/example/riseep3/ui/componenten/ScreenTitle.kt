package com.example.riseep3.ui.componenten

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.riseep3.R
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun ScreenTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = Montagu,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.gold),
            letterSpacing = 1.5.sp
        )
    )
}


@Preview(showBackground = true)
@Composable
fun ScreenTitlePreview() {
    RiseTheme {
        ScreenTitle(title = "Example Title")
    }
}