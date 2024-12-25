package com.example.noteapp.feature_note.presenter.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.noteapp.ui.theme.Colors

@Composable
fun AddNoteDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    addNoteClick: (String, String) -> Unit
) {
    val (title, setTitle) = remember {
        mutableStateOf("")
    }
    val (note, setNote) = remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(4))
                .background(Colors.dark200)
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "add note",
                fontSize = 18.sp,
                color = Colors.white
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "title") },
                value = title,
                onValueChange = { setTitle(it) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Colors.white,
                    focusedLabelColor = Colors.white,
                    unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "note") },
                value = note,
                onValueChange = { setNote(it) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Colors.white,
                    focusedLabelColor = Colors.white,
                    unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                )
            )
            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && note.isNotEmpty()) {
                        addNoteClick.invoke(title, note)
                    }
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}