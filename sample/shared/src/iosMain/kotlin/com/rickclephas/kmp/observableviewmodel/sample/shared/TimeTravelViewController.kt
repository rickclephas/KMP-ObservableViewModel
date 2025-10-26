package com.rickclephas.kmp.observableviewmodel.sample.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun TimeTravelViewController(viewModel: TimeTravelViewModel) = ComposeUIViewController {
    MaterialTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            TimeTravelScreen(viewModel)
        }
    }
}
