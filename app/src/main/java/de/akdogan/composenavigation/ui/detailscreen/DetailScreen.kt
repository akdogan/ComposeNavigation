package de.akdogan.composenavigation.ui.detailscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel,
    modifier: Modifier = Modifier,
    itemId: Int,
) {
    LaunchedEffect(key1 = itemId) {
        // todo use savedStateHandle
        viewModel.loadData(itemId)
    }

    val info by viewModel.detailInfo.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val infoItem = info
        // todo some loading
        if (infoItem != null) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                model = infoItem.imageUrl, contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 48.dp, top = 24.dp)
            ) {
                Text(text = infoItem.name, style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.size(24.dp))
                infoItem.species.takeIf { !it.isNullOrBlank() }?.let { InfoTextItem("Species" to it) }
                infoItem.type.takeIf { !it.isNullOrBlank() }?.let { InfoTextItem("Type" to it) }
                infoItem.status.takeIf { !it.isNullOrBlank() }?.let { InfoTextItem("Status" to it) }
                infoItem.location.takeIf { !it.isNullOrBlank() }?.let { InfoTextItem("Location" to it) }
                infoItem.origin.takeIf { !it.isNullOrBlank() }?.let { InfoTextItem("Origin" to it) }
                InfoTextItem("Number of Episodes" to infoItem.numEpisodes.toString())
            }
        }
    }
}

@Composable
private fun InfoTextItem(
    text: Pair<String, String>
) {
    Row {
        val (description, content) = text
        Text(text = "$description:", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp))
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = content, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp))
    }
    Spacer(modifier = Modifier.size(16.dp))
}
