package com.example.itemizer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.itemizer.R
import com.example.itemizer.ui.screen.HomeScreen
import com.example.itemizer.ui.screen.ItemizerViewModel

@Composable
fun ItemizerApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            val itemizerViewModel: ItemizerViewModel =
                viewModel(factory = ItemizerViewModel.Factory)
            HomeScreen(
                itemizerUiState = itemizerViewModel.itemizerUiState,
                retryAction = itemizerViewModel::getItems
            )
        }
    }
}