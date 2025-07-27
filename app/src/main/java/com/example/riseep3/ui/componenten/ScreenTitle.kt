package com.example.riseep3.ui.componenten

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.riseep3.R
import com.example.riseep3.ui.theme.Montagu

@Composable
fun ScreenTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = Montagu,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.gold),
            letterSpacing = 1.5.sp
        )
    )
}