package com.kklabs.karam.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kklabs.karam.domain.model.karamColors
import com.kklabs.karam.util.TaskIcons
import com.kklabs.karam.util.fetchIcon
import android.graphics.Color as AColor

@Composable
fun ColorsGrid(
    modifier: Modifier = Modifier,
    selectedColor: String,
    onColorSelect: (String) -> Unit
) {

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(48.dp)
    ) {
        items(karamColors) { color ->
            ColorItem(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                color = color.value,
                selected = selectedColor == color.value,
                onColorClick = { onColorSelect(it) }
            )
        }
    }
}

@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    color: String,
    selected: Boolean,
    onColorClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onColorClick(color) }
            .background(Color(AColor.parseColor(color)))
            .border(
                width = if (selected) 1.dp else 0.dp,
                shape = RectangleShape,
                color = Color.Black
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {

    }
}