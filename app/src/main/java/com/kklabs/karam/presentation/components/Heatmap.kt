package com.kklabs.karam.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
fun Heatmap(
    modifier: Modifier = Modifier,
    data: Map<Long, Int>,
    baseColor: String
) {
    val horizontalScrollState = rememberScrollState()
    Row(
        modifier = modifier
            .horizontalScroll(horizontalScrollState)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MonthNamesRow()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val heatmapColumns = data.entries.chunked(7)
                heatmapColumns.fastForEachIndexed { index, weekdata ->
                    HeatmapColumn(weekData = weekdata, baseColor)
                }
            }
        }
    }
}

@Composable
fun MonthNamesRow() {
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        months.forEach { month ->
            TextC70(
                text = month,
                color = Color.Black
            )
        }
    }
}

@Composable
fun HeatmapColumn(weekData: List<Map.Entry<Long, Int>>, baseColor: String) {
    Column {
        weekData.forEach { (date, count) ->
            HeatmapBox(count = count, baseColor = baseColor) {

            }
        }
    }
}

@Composable
fun HeatmapBox(
    count: Int,
    baseColor: String,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(RoundedCornerShape(8.dp))
            .padding(2.dp)
            .clickable {
                onItemClick.invoke()
            }
            .background(Color.Green)
//            .background(generateCommitColor(count))
    )
}


@Preview
@Composable
fun HeatmapPreview() {
    val testData = mutableMapOf<Long, Int>().apply {
        for (i in 1..365) {
            put(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000, (1..10).random())
        }
    }
    Heatmap(data = testData, baseColor = "#FFFFFF")
}