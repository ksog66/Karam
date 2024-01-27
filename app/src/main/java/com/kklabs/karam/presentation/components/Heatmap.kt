package com.kklabs.karam.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun Heatmap(
    modifier: Modifier = Modifier,
    data: Map<Long, Int>,
    baseColor: String
) {
    val horizontalScrollState = rememberScrollState()

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(horizontalScrollState)
            .padding(horizontal = 8.dp)
    ) {
        val (monthRow, heatmapContent) = createRefs()

        MonthNamesRow(modifier = Modifier
            .constrainAs(monthRow) {
                start.linkTo(heatmapContent.start)
                end.linkTo(heatmapContent.end)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(heatmapContent) {
                    top.linkTo(monthRow.bottom, margin = 4.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                },
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            val heatmapColumns = data.entries.chunked(7)
            heatmapColumns.fastForEachIndexed { index, weekdata ->
                HeatmapColumn(weekData = weekdata, baseColor)
            }
        }
    }
}

@Composable
fun MonthNamesRow(modifier: Modifier = Modifier) {
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec) = createRefs()

        val monthRefs = listOf(jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec)
        createHorizontalChain(*monthRefs.toTypedArray(), chainStyle = ChainStyle.SpreadInside)

        months.fastForEachIndexed { index, month ->
            val currentMonthRef = monthRefs[index]

            TextC70(
                text = month,
                color = Color.Black,
                modifier = Modifier
                    .constrainAs(currentMonthRef) {
                        if (index == 0) {
                            start.linkTo(parent.start)
                        } else {
                            start.linkTo(monthRefs[index - 1].end, margin = 2.dp)
                        }

                        if (index == monthRefs.size -1) {
                            end.linkTo(parent.end)
                        } else {
                            end.linkTo(currentMonthRef.start, margin = 2.dp)
                        }

                    }
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
            .size(16.dp)
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