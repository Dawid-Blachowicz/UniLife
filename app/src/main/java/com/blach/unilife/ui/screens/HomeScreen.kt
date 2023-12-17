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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.FeatureTile
import com.blach.unilife.ui.navigation.Routes

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold (
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.app_title),
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
                Spacer(modifier = Modifier.height(60.dp))

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    FeatureTile(
                        textValue = stringResource(R.string.callendar),

                                painterResource = painterResource(id = R.drawable.callendar),
                        onClick = { navController.navigate(Routes.CALENDAR_SCREEN) },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FeatureTile(
                        textValue = stringResource(R.string.to_do),
                        painterResource = painterResource(id = R.drawable.todo),
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)

                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    FeatureTile(
                        textValue = stringResource(R.string.notes),
                        painterResource = painterResource(id = R.drawable.notes),
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FeatureTile(
                        textValue = stringResource(R.string.projects),
                        painterResource = painterResource(id = R.drawable.projects),
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f)
                    )
                }


            }
        }
    }
}