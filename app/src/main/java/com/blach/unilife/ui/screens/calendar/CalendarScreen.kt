package com.blach.unilife.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.components.AddNewEventButton
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.CalendarMonth
import com.blach.unilife.ui.components.DayEventsDialog
import com.blach.unilife.ui.components.DaysOfWeekRow
import com.blach.unilife.ui.components.DisplayAndChangeMonthRow
import com.blach.unilife.ui.data.calendar.CalendarUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.calendar.CalendarViewModel

@Composable
fun CalendarScreen(navController: NavController, viewModel: CalendarViewModel) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.callendar),
                homeButtonClicked = { navController.navigate(Routes.HOME_SCREEN) }
            )
        },
        floatingActionButton = {
            AddNewEventButton(
                value = stringResource(R.string.add_new_event),
                onButtonClicked = { navController.navigate(Routes.NEW_CALENDAR_EVENT_SCREEN) }
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
                        events = uiState.eventsForSelectedDay
                    )
                }

            }
        }
    }

}
