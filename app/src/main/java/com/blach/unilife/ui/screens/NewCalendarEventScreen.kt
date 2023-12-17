package com.blach.unilife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.ButtonComponent
import com.blach.unilife.ui.components.EventDescriptionTextField
import com.blach.unilife.ui.components.EventTextField
import com.blach.unilife.ui.components.MyDatePicker
import com.blach.unilife.ui.components.MyTimePicker
import com.blach.unilife.ui.components.WeekDayDropdown
import com.blach.unilife.ui.data.CalendarEventUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.NewCalendarEventViewModel

@Composable
fun NewCalendarEventScreen(navController: NavController, viewModel: NewCalendarEventViewModel) {

    Scaffold (
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(R.string.app_new_event),
                homeButtonClicked = {
                    navController.navigate(Routes.HOME_SCREEN)
                })
        },

        ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                val uiState by viewModel.uiState.collectAsState()

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.courses))
                    Spacer(modifier = Modifier.width(4.dp))
                    Switch(checked = uiState.isAcademic, onCheckedChange = {
                        viewModel.onEvent(CalendarEventUIEvent.IsAcademicChanged(it))
                    })
                }

                EventTextField(
                    labelValue = stringResource(id = R.string.event_title),
                    textValue = uiState.title,
                    onTextChange = {
                        viewModel.onEvent(CalendarEventUIEvent.TitleChanged(it))
                    })

                Button(onClick = { viewModel.onEvent(CalendarEventUIEvent.OpenStartTimePickerDialog) })  {
                    Text(text = stringResource(R.string.start_time))
                }
                if (uiState.isStartTimePickerDialogOpen) {
                    MyTimePicker(
                        isDialogOpen = uiState.isStartTimePickerDialogOpen,
                        onDialogDismiss = {
                            viewModel.onEvent(CalendarEventUIEvent.CloseStartTimePickerDialog)
                        },
                        onTimeSelected = { newTime ->
                            viewModel.onEvent(CalendarEventUIEvent.StartTimeChanged(newTime))
                            viewModel.onEvent(CalendarEventUIEvent.CloseStartTimePickerDialog)
                        },
                        initialTime = uiState.startTime
                    )
                }

                Button(onClick = { viewModel.onEvent(CalendarEventUIEvent.OpenEndTimePickerDialog) })  {
                    Text(text = stringResource(R.string.end_time))
                }
                if (uiState.isStartTimePickerDialogOpen) {
                    MyTimePicker(
                        isDialogOpen = uiState.isEndTimePickerDialogOpen,
                        onDialogDismiss = {
                            viewModel.onEvent(CalendarEventUIEvent.CloseEndTimePickerDialog)
                        },
                        onTimeSelected = { newTime ->
                            viewModel.onEvent(CalendarEventUIEvent.EndTimeChanged(newTime))
                            viewModel.onEvent(CalendarEventUIEvent.CloseEndTimePickerDialog)
                        },
                        initialTime = uiState.startTime
                    )
                }


                if(uiState.isAcademic) {
                    val weekDays = listOf(
                        stringResource(id = R.string.monday),
                        stringResource(id = R.string.tuesday),
                        stringResource(id = R.string.wednesday),
                        stringResource(id = R.string.thursday),
                        stringResource(id = R.string.friday),
                        stringResource(id = R.string.saturday),
                        stringResource(id = R.string.sunday)
                    )

                    WeekDayDropdown(
                        label = stringResource(id = R.string.event_course_day),
                        options = weekDays,
                        selectedDay = uiState.day,
                        isDropdownExpanded = uiState.isWeekDayDropDownExpanded,
                        onDaySelected = {day ->
                            val selectedDay = viewModel.dayOfWeekMap[day] ?: return@WeekDayDropdown
                            viewModel.onEvent(CalendarEventUIEvent.DayChanged(selectedDay)) },
                        onToggleDropdown = {
                            viewModel.onEvent(CalendarEventUIEvent.ToggleWeekDayDropDown)
                        })

                    EventTextField(
                        labelValue = stringResource(id = R.string.event_professsor),
                        textValue = uiState.professor,
                        onTextChange = {
                            viewModel.onEvent(CalendarEventUIEvent.ProfessorChanged(it))
                        })


                    EventTextField(
                        labelValue = stringResource(id = R.string.event_building),
                        textValue = uiState.building,
                        onTextChange = {
                            viewModel.onEvent(CalendarEventUIEvent.BuildingChanged(it))
                        })

                    EventTextField(
                        labelValue = stringResource(id = R.string.event_room),
                        textValue = uiState.roomNumber,
                        onTextChange = {
                            viewModel.onEvent(CalendarEventUIEvent.RoomNumberChanged(it))
                        })
                } else {
                    Button(onClick = { viewModel.onEvent(CalendarEventUIEvent.OpenDatePickerDialog) })  {
                        Text(text = stringResource(R.string.date))
                    }
                    if (uiState.isDatePickerDialogOpen) {
                        MyDatePicker(
                            isDialogOpen = uiState.isDatePickerDialogOpen,
                            onDialogDismiss = {
                                viewModel.onEvent(CalendarEventUIEvent.CloseDatePickerDialog)
                            },
                            onDateSelected = { newDate ->
                                viewModel.onEvent(CalendarEventUIEvent.DateChanged(newDate))
                                viewModel.onEvent(CalendarEventUIEvent.CloseDatePickerDialog)
                            },
                            initialDate = uiState.date
                        )
                    }
                }

                EventDescriptionTextField(
                    labelValue = stringResource(id = R.string.event_description),
                    textValue = uiState.description,
                    onTextChange = {
                        viewModel.onEvent(CalendarEventUIEvent.DescriptionChanged(it))
                    })

                Spacer(modifier = Modifier.height(30.dp))
                ButtonComponent(value = stringResource(R.string.event_save), isEnabled = true, onButtonClicked = {
                    viewModel.onEvent(CalendarEventUIEvent.SaveButtonClicked)
                    navController.navigate(Routes.CALENDAR_SCREEN)
                })
            }
        }
    }
}