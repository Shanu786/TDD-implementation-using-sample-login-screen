package com.example.dashboardscren.viewmodel

import com.example.core.domain.model.DashboardItem
import com.example.core.domain.repository.DashboardRepository
import com.example.dashboardscren.domain.usecase.GetDashboardItemsUseCase
import com.example.dashboardscren.ui.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun loadDashboardItems_success_exposesItems() = runTest(mainDispatcherRule.dispatcher) {
        val expectedItems = listOf(sampleItem)
        val viewModel =
            DashboardViewModel(GetDashboardItemsUseCase(FakeRepository(Result.success(expectedItems))))

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(expectedItems, viewModel.uiState.value.items)
        assertEquals(null, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun loadDashboardItems_failure_exposesErrorState() = runTest(mainDispatcherRule.dispatcher) {
        val viewModel = DashboardViewModel(
            GetDashboardItemsUseCase(FakeRepository(Result.failure(IllegalStateException("bad data"))))
        )

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isLoading)
        assertNotNull(viewModel.uiState.value.errorMessage)
        assertEquals(emptyList<DashboardItem>(), viewModel.uiState.value.items)
    }

    private class FakeRepository(
        private val result: Result<List<DashboardItem>>,
    ) : DashboardRepository {
        override suspend fun getDashboardItems(): List<DashboardItem> = result.getOrThrow()
        override suspend fun getDashboardItem(itemId: Int): DashboardItem? =
            result.getOrThrow().firstOrNull { it.id == itemId }
    }

    private companion object {
        val sampleItem = DashboardItem(
            id = 1,
            userId = 25,
            postTitle = "Post",
            body = "Body",
            photoTitle = "Photo",
            url = "full.jpg",
            thumbnailUrl = "thumb.jpg",
        )
    }
}
