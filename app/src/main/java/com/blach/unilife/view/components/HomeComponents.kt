package com.blach.unilife.view.components

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.view.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    topBarTitle: String,
    homeButtonClicked: () -> Unit,
    menuButtonClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.purple_500),
            titleContentColor = Color.White
        ),
        title = {
            Text(
                modifier = Modifier.padding(4.dp),
                text = topBarTitle,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
        },
        navigationIcon = {
            IconButton(onClick = menuButtonClicked) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.menu),
                    tint =  Color.White
                )
            }
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
        colors = CardDefaults.cardColors(colorResource(id = R.color.note)),
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

@Composable
fun MyNavigationDrawer(
    navController: NavController
) {
    ModalDrawerSheet {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold
        )
        Divider()
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.callendar)) },
            icon = { Icon(
                painter = painterResource(id = R.drawable.callendar),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            ) },
            selected = false,
            onClick = { navController.navigate(Routes.CALENDAR_SCREEN) }
        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.to_do)) },
            icon = { Icon(
                painter = painterResource(id = R.drawable.todo),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            ) },
            selected = false,
            onClick = { navController.navigate(Routes.TODO_TABS_SCREEN) }
        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.notes)) },
            icon = { Icon(
                painter = painterResource(id = R.drawable.notes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
                ) },
            selected = false,
            onClick = { navController.navigate(Routes.NOTES_SCREEN) }
        )
        NavigationDrawerItem(
            label = { Text(text = stringResource(id = R.string.expense_tracker)) },
            icon = { Icon(
                painter = painterResource(id = R.drawable.projects),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
                ) },
            selected = false,
            onClick = { navController.navigate(Routes.EXPENSE_TRACKER_SCREEN) }
        )
    }
}