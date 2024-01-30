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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(50.dp)
    ) {
        items(AllTaskIcons) { iconName ->
            IconItem(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                iconName = iconName,
                selected = selectedIcon == iconName,
                onIconClick = { onIconSelect(it) }
            )
        }
    }
}

@Composable
fun IconItem(
    modifier: Modifier = Modifier,
    iconName: String,
    selected: Boolean,
    onIconClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (selected) 2.dp else 0.dp,
                shape = RoundedCornerShape(8.dp),
                color = Color.Black
            )
            .background(Color.Gray)
            .clickable { onIconClick(iconName) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(fetchIcon(iconName)),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}