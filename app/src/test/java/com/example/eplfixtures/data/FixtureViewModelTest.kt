package com.example.eplfixtures.data

import com.example.eplfixtures.data.model.Fixture
import com.example.eplfixtures.data.repository.FixtureRepository
import com.example.eplfixtures.ui.viewmodel.FixtureViewModel
import com.example.eplfixtures.ui.viewmodel.FixturesUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FixtureViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val sampleFixtures = listOf(
        Fixture(1, 1, "2023-08-11 19:00:00Z", "Turf Moor", "Burnley", "Manchester City", null, 0, 3),
        Fixture(2, 1, "2023-08-12 12:00:00Z", "Emirates Stadium", "Arsenal", "Nottingham Forest", null, null, null)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFixtures emits Success state with data from repository`() = runTest {
        val repository = mockk<FixtureRepository>()
        coEvery { repository.getFixtures(any()) } returns sampleFixtures

        val viewModel = FixtureViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is FixturesUiState.Success)
        assertEquals(2, (state as FixturesUiState.Success).fixtures.size)
    }

    @Test
    fun `search filters fixtures by team name`() = runTest {
        val repository = mockk<FixtureRepository>()
        coEvery { repository.getFixtures(any()) } returns sampleFixtures

        val viewModel = FixtureViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("arsenal")

        val state = viewModel.uiState.value as FixturesUiState.Success
        assertEquals(1, state.fixtures.size)
        assertEquals("Arsenal", state.fixtures.first().homeTeam)
    }

    @Test
    fun `loadFixtures emits Error state when repository throws`() = runTest {
        val repository = mockk<FixtureRepository>()
        coEvery { repository.getFixtures(any()) } throws RuntimeException("network down")

        val viewModel = FixtureViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is FixturesUiState.Error)
    }
}
