package com.blach.unilife.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blach.unilife.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(
    toolBarTitle: String,
    homeButtonClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.purple_500),
            titleContentColor = Color.White
        ),
        title = {
            Text(
                modifier = Modifier.padding(4.dp),
                text = toolBarTitle,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Filled.Menu,
                contentDescription = stringResource(R.string.menu),
                tint =  Color.White
            )
        },
        actions = {
            IconButton(onClick = {
                homeButtonClicked()
            }) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(R.string.return_to_home),
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun FeatureTile(
    textValue: String,
    painterResource: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource,
                contentDescription = textValue)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textValue,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}