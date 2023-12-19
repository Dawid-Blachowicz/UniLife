package com.blach.unilife.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.blach.unilife.ui.components.NoteItem
import com.blach.unilife.ui.data.NotesUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.NotesViewModel

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(id = R.string.notes),
                homeButtonClicked = {
                    navController.navigate(Routes.HOME_SCREEN)
                })
        },
        floatingActionButton = {
            ActionButton(
                icon = Icons.Default.Add,
                onClick = {
                    navController.navigate(Routes.SINGLE_NOTE_SCREEN)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                val uiState by viewModel.uiState.collectAsState()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.notesForLoggedUser.size) {index ->
                        val note = uiState.notesForLoggedUser[index]
                        NoteItem(
                            note = note,
                            onClick = {
                                viewModel.onEvent(NotesUIEvent.LeadNote(note))
                                navController.navigate(Routes.SINGLE_NOTE_SCREEN)
                            },
                            onLongClick = {
                                viewModel.onEvent(NotesUIEvent.LeadNote(note))
                                viewModel.onEvent(NotesUIEvent.OpenDeleteDialogChanged(true))
                            })
                    }
                }

                if(uiState.openDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            viewModel.onEvent(NotesUIEvent.OpenDeleteDialogChanged(false))
                        },
                        title = {
                                Text(text = stringResource(R.string.note_delete_confirm_question))
                        },
                        confirmButton = { 
                            TextButton(onClick = {
                                viewModel.onEvent(NotesUIEvent.ConfirmDeleteButtonClicked)
                            }) {
                                Text(text = stringResource(R.string.delete))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                viewModel.onEvent(NotesUIEvent.OpenDeleteDialogChanged(false))
                            }) {
                                Text(text = stringResource(R.string.cancell))
                            }
                        }
                    )
                }
            }
        }
    }
}

