package com.example.noteapp.feature_note.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.presenter.composables.AddNoteDialog
import com.example.noteapp.feature_note.presenter.composables.HomeScreenComposable

@Composable
fun HomeScreen(
    viewModel: MainVM = hiltViewModel()
) {

    val notes by viewModel.notes.collectAsState()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        AddNoteDialog(
            onDismissRequest = { setDialogOpen(false) },
            addNoteClick = { title, note ->
                viewModel.insertNote(
                    Note(title = title, noteText = note)
                )
                setDialogOpen(false)
            }
        )
    }

    HomeScreenComposable(
        notes = notes,
        onAddNoteClick = { setDialogOpen(true) },
        onNoteClick = { note ->
            viewModel.updateNote(
                note.copy(isDone = !note.isDone)
            )
        },
        onDeleteClick = viewModel::deleteNote
    )
}