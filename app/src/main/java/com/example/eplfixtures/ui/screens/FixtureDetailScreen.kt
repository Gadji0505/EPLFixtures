package com.example.eplfixtures.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.eplfixtures.ui.viewmodel.FixtureViewModel
import com.example.eplfixtures.ui.viewmodel.FixturesUiState
import com.example.eplfixtures.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixtureDetailScreen(
    matchNumber: Int,
    viewModel: FixtureViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    // Дожидаемся, пока общий список матчей загрузится (или уже в кеше ViewModel),
    // и достаём нужный матч по номеру.
    val fixture = if (uiState is FixturesUiState.Success) {
        viewModel.getFixtureByMatchNumber(matchNumber)
    } else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали матча") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (fixture == null) {
                if (uiState is FixturesUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text("Матч не найден")
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Тур ${fixture.roundNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = fixture.homeTeam,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = fixture.scoreText,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        Text(
                            text = fixture.awayTeam,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(label = "Дата", value = DateUtils.toLocalDate(fixture.dateUtc))
                    DetailRow(label = "Время", value = DateUtils.toLocalTime(fixture.dateUtc))
                    DetailRow(label = "Локация", value = fixture.location ?: "Не указана")
                    DetailRow(
                        label = "Статус",
                        value = if (fixture.isPlayed) "Матч сыгран" else "Матч предстоит"
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}
