package com.kklabs.karam.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.kklabs.karam.R
import com.kklabs.karam.data.remote.response.TasksKaram
import com.kklabs.karam.domain.model.Task
import com.kklabs.karam.presentation.components.Heatmap
import com.kklabs.karam.presentation.components.TextH20
import com.kklabs.karam.presentation.components.TextH50
import com.kklabs.karam.ui.theme.KaramTheme
import com.kklabs.karam.util.fetchIcon
import java.time.Instant
import java.util.Date

@Composable
fun TaskDisplay(
    modifier: Modifier = Modifier,
    task: TasksKaram?,
    type: TaskDisplayType,
    tasklogs: Map<Long, Int>
) {
    val isIndividual = type == TaskDisplayType.INDIVIDUAL
    val title = when(type) {
        TaskDisplayType.CUMULATIVE -> {
            stringResource(id = R.string.all_karam)
        }
        TaskDisplayType.INDIVIDUAL -> {
            task!!.name
        }
    }
    val baseColor = when(type) {
        TaskDisplayType.CUMULATIVE -> {
            "#28a745"
        }
        TaskDisplayType.INDIVIDUAL -> {
            task!!.color
        }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(vertical = 8.dp)
                .zIndex(100f)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(fetchIcon(task?.icon)), // Replace with the actual drawable resource
                            contentDescription = null, // Provide a meaningful content description
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextH20(text = title )
                }
                if (isIndividual) {
                    Button(
                        onClick = { /* Handle log click */ },
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        )
                    ) {
                        TextH50(text = "Log", color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Heatmap(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                data = tasklogs,
                baseColor = baseColor
            )
        }
    }
}

enum class TaskDisplayType {
    CUMULATIVE,
    INDIVIDUAL
}

@Preview
@Composable
fun TaskDisplayPreview() {
    KaramTheme {
        val testData = mutableMapOf<Long, Int>().apply {
            for (i in 1..365) {
                put(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000, (1..10).random())
            }
        }
        val task = TasksKaram(
            "#FFFFFF",
            "GYM",
            2,
            testData,
            "Gym"
        )

        TaskDisplay(task = task, tasklogs = testData, type = TaskDisplayType.INDIVIDUAL)
    }
}