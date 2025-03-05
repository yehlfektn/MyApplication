package com.example.myapplication.ui.countries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.myapplication.domain.model.CharacterUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    if (uiState.errorHolder.isError) {
        ErrorDialogView(
            errorMessage = uiState.errorHolder.errorMessage,
            onDismiss = { viewModel.dismissError() },
            onRetry = { viewModel.retry() },
        )
    }
    PullToRefreshBox(
        state = pullRefreshState,
        onRefresh = {
            viewModel.refreshData()
        },
        modifier = Modifier.fillMaxSize(),
        isRefreshing = uiState.isLoading
    ) {
        if (uiState.characters.isNotEmpty()) {
            Column {
                Text(
                    modifier = Modifier.padding(all = 12.dp),
                    text = "This is a list of Rick and Morty characters:"
                )
                LazyColumn {
                    items(uiState.characters.size) { index ->
                        val character = uiState.characters[index]
                        CharacterItem(
                            character = character,
                            onClick = { viewModel.onCharacterClick(character.id) }
                        )
                    }
                }
            }
        } else {
            Text(modifier = Modifier.fillMaxWidth(),
                text = "Nothing to show.")
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(
    character: CharacterUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            ) {
                it.thumbnail()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Status: ${character.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (character.showFullInfo) {
                    Text(
                        text = "Species: ${character.species}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Gender: ${character.gender}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Origin: ${character.origin.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Location: ${character.location.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorDialogView(
    errorMessage: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Attention!") },
        text = { Text(errorMessage) },
        confirmButton = {
            Row {
                TextButton(onClick = onRetry) {
                    Text("Try again")
                }
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    )
}