package com.example.eplfixtures.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eplfixtures.data.model.Fixture
import com.example.eplfixtures.data.repository.FixtureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Состояние экрана списка матчей (MVVM).
 */
sealed class FixturesUiState {
    data object Loading : FixturesUiState()
    data class Success(val fixtures: List<Fixture>) : FixturesUiState()
    data class Error(val message: String) : FixturesUiState()
}

class FixtureViewModel(
    private val repository: FixtureRepository = FixtureRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<FixturesUiState>(FixturesUiState.Loading)
    val uiState: StateFlow<FixturesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Полный список храним отдельно, чтобы локальный поиск не требовал новых запросов к сети
    private var allFixtures: List<Fixture> = emptyList()

    init {
        loadFixtures()
    }

    fun loadFixtures(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = FixturesUiState.Loading
            try {
                val fixtures = repository.getFixtures(forceRefresh)
                allFixtures = fixtures
                applySearch(_searchQuery.value)
            } catch (e: Exception) {
                _uiState.value = FixturesUiState.Error(
                    e.message ?: "Не удалось загрузить матчи. Проверьте подключение к интернету."
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applySearch(query)
    }

    private fun applySearch(query: String) {
        val filtered = if (query.isBlank()) {
            allFixtures
        } else {
            allFixtures.filter {
                it.homeTeam.contains(query, ignoreCase = true) ||
                    it.awayTeam.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = FixturesUiState.Success(filtered)
    }

    fun getFixtureByMatchNumber(matchNumber: Int): Fixture? =
        allFixtures.firstOrNull { it.matchNumber == matchNumber }
}
