package com.example.dashboardscren.data.repository

import com.example.dashboardscren.data.datasource.DashboardLocalDataSource
import com.example.dashboardscren.data.model.DashboardItemDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DashboardRepositoryImplTest {
    @Test
    fun getDashboardItems_mapsDataTransferObjectsToDomainItems() = runBlocking {
        val dto = DashboardItemDto(
            id = 4,
            userId = 91,
            postTitle = "Post",
            body = "Body",
            photoTitle = "Photo",
            url = "full.jpg",
            thumbnailUrl = "thumb.jpg",
        )
        val repository = DashboardRepositoryImpl(FakeLocalDataSource(listOf(dto)))

        val result = repository.getDashboardItems()

        assertEquals(4, result.single().id)
        assertEquals(91, result.single().userId)
        assertEquals("Post", result.single().postTitle)
        assertEquals("thumb.jpg", result.single().thumbnailUrl)
    }

    @Test
    fun getDashboardItem_looksUpByItemIdRatherThanUserId() = runBlocking {
        val dto = DashboardItemDto(
            id = 4,
            userId = 91,
            postTitle = "Post",
            body = "Body",
            photoTitle = "Photo",
            url = "full.jpg",
            thumbnailUrl = "thumb.jpg",
        )
        val repository = DashboardRepositoryImpl(FakeLocalDataSource(listOf(dto)))

        assertEquals(4, repository.getDashboardItem(itemId = 4)?.id)
        assertEquals(null, repository.getDashboardItem(itemId = 91))
    }

    private class FakeLocalDataSource(
        private val items: List<DashboardItemDto>,
    ) : DashboardLocalDataSource {
        override suspend fun getDashboardItems() = items
    }
}
