package com.blach.unilife.view.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.view.components.AddNewEventButton
import com.blach.unilife.view.components.AppTopBar
import com.blach.unilife.view.components.CalendarMonth
import com.blach.unilife.view.components.DayEventsDialog
import com.blach.unilife.view.components.DaysOfWeekRow
import com.blach.unilife.view.components.DisplayAndChangeMonthRow
import com.blach.unilife.view.components.MyNavigationDrawer
import com.blach.unilife.view.data.calendar.CalendarUIEvent
import com.blach.unilife.view.navigation.Routes
import com.blach.unilife.viewmodels.calendar.CalendarViewModel
import kotlinx.coroutines.launch

@Composable
fun CalendarScreen(navController: NavController, viewModel: CalendarViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyNavigationDrawer(navController = navController)
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    topBarTitle = stringResource(id = R.string.callendar),
                    homeButtonClicked = {
                        navController.navigate(Routes.HOME_SCREEN)
                    },
                    menuButtonClicked = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                AddNewEventButton(
                    onButtonClicked = { navController.navigate("${Routes.NEW_CALENDAR_EVENT_SCREEN}/add") }
                )
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)) {
                    Spacer(modifier = Modifier.padding(15.dp))

                    val uiState by viewModel.uiState.collectAsState()

                    DisplayAndChangeMonthRow(uiState.currentMonth) { newMonth ->
                        viewModel.onEvent(CalendarUIEvent.CurrentMonthChanged(newMonth))
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    DaysOfWeekRow()
                    Spacer(modifier = Modifier.padding(5.dp))

                    CalendarMonth(
                        currentMonth = uiState.currentMonth,
                        simpleEvents = uiState.simpleEventsForSelectedMonth,
                        onDayClicked = { day ->
                            viewModel.onEvent(CalendarUIEvent.DaySelected(day))
                        })

                    uiState.selectedDay?.let { day ->
                        DayEventsDialog(
                            selectedDay = day,
                            onDismiss = { viewModel.onEvent(CalendarUIEvent.DayDialogDismissed) },
                            events = uiState.eventsForSelectedDay,
                            onDeleteEvent = {
                                viewModel.onEvent(CalendarUIEvent.EventDeleted(it))
                            },
                            onClick = {
                                navController.navigate("${Routes.NEW_CALENDAR_EVENT_SCREEN}/${it}")
                            }
                        )
                    }
                }
            }
        }
    }
}
