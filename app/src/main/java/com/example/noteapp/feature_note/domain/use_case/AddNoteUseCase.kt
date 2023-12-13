package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    suspend fun execute(note: Note) {
        if (note.title.isBlank()) {
            return
        }

        if (note.noteText.isBlank()) {
            return
        }

        repository.insertNote(note)
    }
}