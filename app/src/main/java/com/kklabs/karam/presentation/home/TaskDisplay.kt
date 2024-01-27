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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.kklabs.karam.R
import com.kklabs.karam.domain.model.Task
import com.kklabs.karam.presentation.components.Heatmap
import com.kklabs.karam.presentation.components.TextC70
import com.kklabs.karam.presentation.components.TextH20
import com.kklabs.karam.presentation.components.TextH50
import com.kklabs.karam.ui.theme.KaramTheme
import java.time.Instant
import java.util.Date

@Composable
fun TaskDisplay(
    modifier: Modifier = Modifier,
    task: Task,
    tasklogs: Map<Long, Int>
) {
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
//                    .background(Color(task.color))
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        // Assuming the task.icon is a drawable resource ID
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with the actual drawable resource
                            contentDescription = null, // Provide a meaningful content description
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextH20(text = task.title)
                }
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
            Spacer(modifier = Modifier.height(4.dp))
            Heatmap(
                modifier = Modifier.fillMaxWidth(),
                data = tasklogs,
                baseColor = task.color
            )
        }
    }
}

@Preview
@Composable
fun TaskDisplayPreview() {
    KaramTheme {
        val task = Task(
            1,
            "#FFFFFF",
            Date(),
            null,
            "Gym",
            "Gym",
            1
        )
        val testData = mutableMapOf<Long, Int>().apply {
            for (i in 1..365) {
                put(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000, (1..10).random())
            }
        }
        TaskDisplay(task = task, tasklogs = testData)
    }
}