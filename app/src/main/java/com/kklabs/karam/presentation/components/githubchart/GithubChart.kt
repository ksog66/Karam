package com.kklabs.karam.presentation.components.githubchart

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

val keys = listOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GithubChart(
    chartDetail: ChartDetail,
) {
    val months = remember(chartDetail) {
        JetYear.current(firstDayOfWeek = DayOfWeek.MONDAY).yearMonths
    }

    LazyRow(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ) {
        stickyHeader {
            Column {
                DayOfWeek.values().forEach { dayOfWeek ->
                    Text(
                        dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
                        style = com.kklabs.karam.ui.theme.Typography.bodySmall.copy(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(510),
                            color = Color(0xff242424),
                        ),
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }
        }

        itemsIndexed(months) { index, jetMonth ->
            val datesWithinHabit =
                jetMonth.monthWeeks.map { jetWeek ->
                    jetWeek.days
                }.flatten()
            val groupedDayOfWeekAndDates = datesWithinHabit
                .groupBy { it.date.dayOfWeek }
                .toSortedMap { o1, o2 ->
                    Log.d(
                        "GithubChart", """
                        o1 -> ${o1}  
                        o2 -> ${o2}
                        """.trimIndent()
                    )
                    keys.indexOf(o1.toString()).compareTo(keys.indexOf(o2.toString()))
                }
            val monthHeaderName = when {
                index == 1 && months.getOrNull(index.minus(1))
                    ?.year() != jetMonth.year() -> jetMonth.monthYear()

                else -> jetMonth.name()
            }

            Column(Modifier.padding(start = 12.dp)) {
                Text(monthHeaderName)
                groupedDayOfWeekAndDates.forEach {
                    val days = it.value
                    Row {
                        days.forEach { jetDay ->
                            Box {
                                if (jetDay.isPartOfMonth) {
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(16.dp)
                                            .background(
                                                if (chartDetail.commitDates.contains(jetDay.date)) Color.Yellow else Color.Gray,
                                                shape = RoundedCornerShape(4.dp),
                                            ),
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(16.dp)
                                            .background(
                                                Color.Gray,
                                                shape = RoundedCornerShape(4.dp),
                                            ),
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewGithubChart() {
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            GithubChart(
                ChartDetail(
                    commitDates = JetYear.current(firstDayOfWeek = DayOfWeek.MONDAY).yearMonths.map {
                        it.monthWeeks.map {
                            it.dates().map { it.date }.random()
                        }
                    }.flatten(),
                ),
            )
        }
    }
}

data class ChartDetail(val commitDates: List<LocalDate>)