package de.akdogan.composenavigation.ui.listscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import de.akdogan.composenavigation.LocalNavigator
import de.akdogan.composenavigation.NavigationRoutes
import de.akdogan.composenavigation.ui.listscreen.model.ListCharacter

@Composable
fun ListScreen(
    viewModel: ListScreenViewModel,
    modifier: Modifier = Modifier,
) {
    val characters = viewModel.characters

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = characters,
            key = { index, item -> item.id }
        ) { index, item ->
            ListCharacter(
                modifier = Modifier.padding(8.dp),
                item = item,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ListCharacter(
    modifier: Modifier = Modifier,
    item: ListCharacter,
) {
    val navigator = LocalNavigator.current
    Card(
        modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                navigator.navigateToRoute(NavigationRoutes.Detail(item.id))
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(94.dp)
                    .clip(CircleShape)
                ,
                model = item.imageUrl,
                contentDescription = "",
//            loading = placeholder(painterResource(id = R.drawable.ic_loading_placeholder))
            )
            Spacer(modifier = Modifier.size(24.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                item.originName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
