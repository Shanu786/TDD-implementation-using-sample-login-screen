package com.example.feature_details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core.domain.model.DashboardItem
import com.example.feature_details.ui.model.DetailsUiState
import com.example.feature_details.ui.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(onClick = onBackClick) {
                Text(
                    text = "Back",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Button(onClick = onLogoutClick) {
                Text(
                    text = "Log out",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        when (val state = uiState) {
            DetailsUiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

            DetailsUiState.NotFound -> CenteredMessage("The selected item could not be found.")

            DetailsUiState.Error -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Couldn't load this item. Please try again.")
                Button(
                    onClick = detailsViewModel::loadItem,
                    modifier = Modifier.padding(top = 12.dp),
                ) {
                    Text("Retry")
                }
            }

            is DetailsUiState.Success -> DetailsContent(state.item)
        }
    }
}

@Composable
private fun CenteredMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message)
    }
}

@Composable
private fun DetailsContent(item: DashboardItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),
            model = item.url,
            contentDescription = item.photoTitle.ifBlank { "Item photo" },
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            color = Color.Black,
            text = item.postTitle,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Black,
            text = item.photoTitle,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            color = Color.DarkGray,
            text = item.body,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
