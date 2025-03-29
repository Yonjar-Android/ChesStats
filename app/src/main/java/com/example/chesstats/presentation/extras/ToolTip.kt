package com.example.chesstats.presentation.extras

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun TooltipExample(
    country: String,
    closeToolTip: () -> Unit
) {
            Popup(alignment = Alignment.TopStart, onDismissRequest = {closeToolTip.invoke() }) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black,
                ) {
                    Text(
                        text = country,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
}

