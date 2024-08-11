package com.rickclephas.kmp.observableviewmodel.sample.shared

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.ComposeUIViewController

fun TimeTravelViewController(viewModel: TimeTravelViewModel) = ComposeUIViewController {
    MaterialTheme {
        TimeTravelScreen(viewModel)
    }
}
