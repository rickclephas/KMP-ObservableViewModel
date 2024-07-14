package com.rickclephas.kmp.observableviewmodel.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rickclephas.kmp.observableviewmodel.sample.shared.TimeTravelViewModel

class ComposeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            Surface {
                TimeTravelScreen()
            }
        }
    }

    @Composable
    private fun TimeTravelScreen(viewModel: TimeTravelViewModel = viewModel()) {
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
}
