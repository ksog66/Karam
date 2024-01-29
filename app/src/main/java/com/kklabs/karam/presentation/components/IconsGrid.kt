package com.kklabs.karam.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import com.kklabs.karam.util.TaskIcons.AllTaskIcons
import com.kklabs.karam.util.fetchIcon

@Composable
fun IconsGrid(
    modifier: Modifier = Modifier,
    selectedIcon: String,
    onIconSelect: (String) -> Unit
) {

    LazyHorizontalGrid(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        rows = GridCells.Fixed(3)
    ) {
        items(AllTaskIcons) { iconName ->
            IconItem(
                iconName = iconName,
                selected = selectedIcon == iconName,
                onIconClick = { onIconSelect(it) }
            )
        }
    }
}

@Composable
fun IconItem(iconName: String, selected: Boolean, onIconClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .border(
                width = if (selected) 1.dp else 0.dp,
                shape = RectangleShape,
                color = Color.Black
            )
            .clickable { onIconClick(iconName) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = fetchIcon(iconName)),
            contentDescription = null,
        )
    }
}