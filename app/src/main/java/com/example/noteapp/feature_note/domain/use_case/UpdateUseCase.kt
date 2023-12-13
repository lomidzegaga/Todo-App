package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class UpdateUseCase(
    private val repository: NoteRepository
) {
    suspend fun execute(note: Note) {
        repository.updateNote(note)
    }
}