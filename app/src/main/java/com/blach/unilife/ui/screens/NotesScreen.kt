package com.blach.unilife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blach.unilife.R
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.FeatureTile
import com.blach.unilife.ui.navigation.Routes

@Composable
fun NotesScreen() {
    Scaffold (
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.callendar),
                homeButtonClicked = { /*TODO */ })
        },

        ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "helllooo")
            }
        }
    }
}

@Preview
@Composable
fun NotesScreenPreview() {
    NotesScreen()
}