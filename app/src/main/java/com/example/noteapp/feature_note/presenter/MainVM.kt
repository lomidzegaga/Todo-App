package com.example.noteapp.feature_note.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val noteUserCases: NoteUseCases
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes
        .onStart { getNotes() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun getNotes() {
        viewModelScope.launch {
            noteUserCases.getNotesUseCase.execute().collect { notes ->
                _notes.update { notes  }
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteUserCases.deleteNoteUseCase.execute(note)
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteUserCases.addNoteUseCase.execute(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteUserCases.updateUseCase.execute(note)
        }
    }
}