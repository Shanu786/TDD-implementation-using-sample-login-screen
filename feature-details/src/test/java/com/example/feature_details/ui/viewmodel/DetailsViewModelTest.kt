package com.example.feature_details.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.core.domain.model.DashboardItem
import com.example.core.domain.repository.DashboardRepository
import com.example.feature_details.domain.usecase.GetDashboardItemUseCase
import com.example.feature_details.ui.model.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setMainDispatcher() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun resetMainDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_itemFound_exposesSuccessState() = runTest(dispatcher) {
        val expected = sampleItem
        val repository = FakeRepository(Result.success(expected))
        val viewModel = createViewModel(repository)

        advanceUntilIdle()
        assertEquals(DetailsUiState.Success(expected), viewModel.uiState.value)
        assertEquals(expected.id, repository.requestedItemId)
    }

    @Test
    fun init_itemMissing_exposesNotFoundState() = runTest(dispatcher) {
        val viewModel = createViewModel(FakeRepository(Result.success<DashboardItem?>(null)))

        advanceUntilIdle()
        assertEquals(DetailsUiState.NotFound, viewModel.uiState.value)
    }

    @Test
    fun init_repositoryFails_exposesErrorState() = runTest(dispatcher) {
        val viewModel = createViewModel(
            FakeRepository(Result.failure<DashboardItem?>(IllegalStateException("network failure"))),
        )

        advanceUntilIdle()
        assertEquals(DetailsUiState.Error, viewModel.uiState.value)
    }

    private fun createViewModel(repository: FakeRepository) = DetailsViewModel(
        savedStateHandle = SavedStateHandle(mapOf("itemId" to sampleItem.id)),
        getDashboardItem = GetDashboardItemUseCase(repository),
    )

    private class FakeRepository(
        private val result: Result<DashboardItem?>,
    ) : DashboardRepository {
        var requestedItemId: Int? = null
            private set

        override suspend fun getDashboardItems(): List<DashboardItem> =
            result.getOrThrow()?.let(::listOf).orEmpty()

        override suspend fun getDashboardItem(itemId: Int): DashboardItem? {
            requestedItemId = itemId
            return result.getOrThrow()?.takeIf { it.id == itemId }
        }
    }

    private companion object {
        val sampleItem = DashboardItem(
            id = 7,
            userId = 25,
            postTitle = "A dashboard post",
            body = "The complete item description.",
            photoTitle = "A photo title",
            url = "",
            thumbnailUrl = "",
        )
    }
}
