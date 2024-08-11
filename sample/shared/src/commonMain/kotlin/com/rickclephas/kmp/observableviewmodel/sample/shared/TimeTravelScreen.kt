package com.rickclephas.kmp.observableviewmodel.sample.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Suppress("DuplicatedCode")
fun TimeTravelScreen(viewModel: TimeTravelViewModel) = Surface {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Actual time:")
        val actualTime by viewModel.actualTime.collectAsState()
        Text(actualTime, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Travel effect:")
        val travelEffect by viewModel.travelEffect.collectAsState()
        Text(travelEffect.toString(), fontSize = 20.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text("Current time:")
        val currentTime by viewModel.currentTime.collectAsState()
        Text(currentTime, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(24.dp))

        val isFixedTime by viewModel.isFixedTime.collectAsState()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(isFixedTime, { isChecked ->
                when (isChecked) {
                    true -> viewModel.stopTime()
                    false -> viewModel.startTime()
                }
            })
            Text("Fixed time")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(viewModel::timeTravel) {
            Text("Time travel")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(viewModel::resetTime) {
            Text("Reset")
        }
    }
}
