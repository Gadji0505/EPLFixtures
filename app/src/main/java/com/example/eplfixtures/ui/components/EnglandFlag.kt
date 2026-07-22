package com.example.eplfixtures.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eplfixtures.ui.theme.EPLFixturesTheme

@Composable
fun EnglandFlag(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(5f / 3f)
            .background(Color.White),
    ) {
        // Horizontal bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(Color(0xFFCE1124))
                .align(Alignment.Center),
        )
        // Vertical bar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.12f)
                .background(Color(0xFFCE1124))
                .align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EnglandFlagPreview() {
    EPLFixturesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            EnglandFlag(modifier = Modifier.width(300.dp))
        }
    }
}
