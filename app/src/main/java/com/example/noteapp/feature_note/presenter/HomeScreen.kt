package com.example.noteapp.feature_note.presenter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.model.addDate
import com.example.noteapp.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainVM
) {

    val notes by viewModel.notes.collectAsState()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        val (title, setTitle) = remember {
            mutableStateOf("")
        }
        val (note, setNote) = remember {
            mutableStateOf("")
        }

        Dialog(onDismissRequest = { setDialogOpen(false) }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
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
                    value = title,
                    onValueChange = {
                        setTitle(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "title")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Colors.white,
                        focusedLabelColor = Colors.white,
                        textColor = Colors.white
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = {
                        setNote(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "note")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Colors.white,
                        focusedLabelColor = Colors.white,
                        textColor = Colors.white
                    )
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = {
                        if (title.isNotEmpty() && note.isNotEmpty()) {
                            viewModel.insertNote(
                                Note(
                                    title = title,
                                    noteText = note
                                )
                            )
                            setDialogOpen(false)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }

    Scaffold(
        containerColor = Colors.dark500,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                setDialogOpen(true)
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = notes.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text(
                    text = "No Notes Yet!",
                    color = Colors.white,
                    fontSize = 23.sp
                )
            }

            AnimatedVisibility(
                visible = notes.isNotEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = padding.calculateBottomPadding() + 8.dp,
                            top = 8.dp,
                            end = 8.dp,
                            start = 8.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        notes.sortedBy { it.isDone },
                        key = { it.id }
                    ) { note ->
                        NoteItem(
                            note = note,
                            onClick = {
                                viewModel.updateNote(
                                    note.copy(
                                        isDone = !note.isDone
                                    )
                                )
                            },
                            onDelete = {
                                viewModel.deleteNote(note)
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.NoteItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (note.isDone) Colors.green else Colors.red,
        label = "",
        animationSpec = tween(500)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color)
            .clickable { onClick() }
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
            .animateItemPlacement(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Colors.black),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    AnimatedVisibility(
                        visible = note.isDone,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
                Row {
                    AnimatedVisibility(
                        visible = !note.isDone,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = color
                        )
                    }
                }
            }
            Column {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Colors.white
                )
                Text(
                    text = note.noteText,
                    fontSize = 18.sp,
                    color = Color(0xFFEBEBEB)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Box(modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .background(Colors.black)
                .padding(3.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    tint = Colors.white,
                    modifier = Modifier
                        .clickable { onDelete() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.addDate,
                fontSize = 15.sp,
                color = Colors.black
            )
        }
    }
}







