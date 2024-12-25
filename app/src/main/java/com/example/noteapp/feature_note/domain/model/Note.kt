package com.example.noteapp.feature_note.domain.model

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Stable
@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @ColumnInfo("note_text")
    val noteText: String,
    @ColumnInfo("is_done")
    val isDone: Boolean = false,
    val added: Long = System.currentTimeMillis()
)

val Note.addDate: String get() = SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.US).format(Date(added))
