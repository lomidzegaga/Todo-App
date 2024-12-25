package com.example.noteapp.feature_note.presenter.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.model.addDate
import com.example.noteapp.ui.theme.Colors

@Composable
fun LazyItemScope.NoteItem(
    note: Note,
    onItemClick: () -> Unit,
    onDelete: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (note.isDone) Colors.green else Colors.red,
        label = "ItemColor",
        animationSpec = tween(500)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15))
            .background(color)
            .clickable { onItemClick() }
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
            .animateItem(
                placementSpec = spring(
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