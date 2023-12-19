package com.blach.unilife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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
import com.blach.unilife.ui.components.ActionButton
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.NoteContentField
import com.blach.unilife.ui.components.NoteTitleField
import com.blach.unilife.ui.data.NotesUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.NotesViewModel
import java.time.LocalDate

@Composable
fun SingleNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    Scaffold (
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.notes),
                homeButtonClicked = {
                    viewModel.onEvent(NotesUIEvent.LastEditDateChanged(LocalDate.now()))
                    viewModel.onEvent(NotesUIEvent.SaveButtonClicked)
                    navController.navigate(Routes.HOME_SCREEN)
                })
        },
        floatingActionButton = {
            ActionButton(
                icon = Icons.Default.Done,
                onClick = {
                    viewModel.onEvent(NotesUIEvent.LastEditDateChanged(LocalDate.now()))
                    viewModel.onEvent(NotesUIEvent.SaveButtonClicked)
                    navController.navigate(Routes.NOTES_SCREEN)
                }
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
                .padding(8.dp)
            ) {
                val uiState by viewModel.uiState.collectAsState()

                NoteTitleField(
                    label = "Tytuł",
                    text = uiState.title, 
                    onTextChange = {
                        viewModel.onEvent(NotesUIEvent.TitleChanged(it))
                    })
                Spacer(modifier = Modifier.height(8.dp))
                NoteContentField(
                    label = "Treść",
                    text = uiState.content,
                    onTextChange = {
                        viewModel.onEvent(NotesUIEvent.ContentChanged(it))
                    })
            }
        }
    }
}
