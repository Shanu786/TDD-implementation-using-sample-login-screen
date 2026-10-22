package com.example.dashboardscren.data.datasource

import android.content.Context
import com.example.dashboardscren.data.model.DashboardItemDto
import com.example.dashboardscren.data.model.DashboardItemJsonDecoder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DashboardLocalDataSource {

    override suspend fun getDashboardItems(): List<DashboardItemDto> = withContext(Dispatchers.IO) {
        val jsonData = context.assets.open(ASSET_FILE_NAME).bufferedReader().use { it.readText() }
        DashboardItemJsonDecoder.decode(jsonData)
    }

    private companion object {
        const val ASSET_FILE_NAME = "json_sample_response.json"
    }
}
