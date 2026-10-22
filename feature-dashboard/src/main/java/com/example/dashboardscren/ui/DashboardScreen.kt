package com.example.dashboardscren.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.core.domain.model.DashboardItem
import com.example.dashboardscren.ui.model.DashboardUiState
import com.example.dashboardscren.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onLogoutClick: () -> Unit,
    onItemClick: (DashboardItem) -> Unit,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by dashboardViewModel.uiState.collectAsStateWithLifecycle()

    DashboardScreen(
        uiState = uiState,
        onLogoutClick = onLogoutClick,
        onRetryClick = dashboardViewModel::loadDashboardItems,
        onItemClick = onItemClick
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onLogoutClick: () -> Unit,
    onRetryClick: () -> Unit,
    onItemClick: (DashboardItem) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Button(onClick = onLogoutClick) {
                Text(
                    text = "Log out",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        when {
            uiState.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(uiState.errorMessage)
                Button(onClick = onRetryClick, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Retry")
                }
            }

            uiState.items.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("No dashboard items available.")
            }

            else -> DashboardItemList(
                items = uiState.items,
                modifier = Modifier.weight(1f),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun DashboardItemList(
    items: List<DashboardItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DashboardItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = items, key = DashboardItem::id) { item ->
            DashboardItemRow(item, onItemClick)
        }
    }
}

@Composable
private fun DashboardItemRow(item: DashboardItem, onItemClick: (DashboardItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(item)
            },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = item.thumbnailUrl,
                contentDescription = item.photoTitle.ifBlank { "Photo thumbnail" },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading ->
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        }

                    is AsyncImagePainter.State.Error ->
                        Box(contentAlignment = Alignment.Center) {
                            Text("Image unavailable", style = MaterialTheme.typography.labelSmall)
                        }

                    else -> SubcomposeAsyncImageContent()
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
            ) {
                Text(
                    text = item.postTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.photoTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
