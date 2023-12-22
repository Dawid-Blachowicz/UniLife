package com.blach.unilife.ui.screens.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.components.ActionButton
import com.blach.unilife.ui.components.AddTaskDialog
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.TodoTabRow
import com.blach.unilife.ui.data.todo.TodoUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.todo.TodoViewModel

@Composable
fun TodoTabsScreen(navController: NavController, viewModel: TodoViewModel) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(R.string.todo),
                homeButtonClicked = {
                    navController.navigate(Routes.HOME_SCREEN)
                })
        },
        floatingActionButton = {
            ActionButton(
                icon = Icons.Default.Add,
                onClick = {
                    viewModel.onEvent(TodoUIEvent.OpenTaskDialogChanged(true))
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
            Column {
                val uiState by viewModel.uiState.collectAsState()
                TodoTabRow(
                    selectedTabIndex = uiState.selectedTabIndex,
                    onTabSelected = {
                        viewModel.onEvent(TodoUIEvent.SelectedTabIndexChanged(it))
                    })

                when (uiState.selectedTabIndex) {
                    0 -> TodoScreen(viewModel = viewModel)
                    1 -> TodoDoneScreen(viewModel = viewModel)
                }

                if(uiState.openTaskDialog) {
                    AddTaskDialog(
                        taskContent = uiState.content,
                        onTaskContentChange = {
                            viewModel.onEvent(TodoUIEvent.ContentChanged(it))
                        },
                        onDismissRequest = {
                            viewModel.onEvent(TodoUIEvent.OpenTaskDialogChanged(false))
                        },
                        onConfirm = {
                            viewModel.onEvent(TodoUIEvent.SaveButtonClicked)
                        }
                    )
                }
            }
        }
    }
}