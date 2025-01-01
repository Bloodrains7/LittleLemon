package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LittleLemonTopAppBar(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit = {}, actions: @Composable RowScope.() -> Unit = {}) {
    CenterAlignedTopAppBar(modifier = modifier, title = {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .widthIn(200.dp)
                .fillMaxHeight()
        )
    }, actions = actions, navigationIcon = navigationIcon)
}

@Preview(showBackground = true)
@Composable
fun LittleLemonTopAppBarPreview() {
    LittleLemonTopAppBar()
}