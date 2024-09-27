package de.akdogan.composenavigation.ui.welcomescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.akdogan.composenavigation.LocalNavigator
import de.akdogan.composenavigation.NavigationRoutes

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome! Here we could do some basic intro slides or ask for tracking or notifications or something like that.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(48.dp))
        val navigator = LocalNavigator.current
        Button(
            onClick = {
                navigator.navigateToRoute(
                    route = NavigationRoutes.List,
                    popUpToRouteInclusive = NavigationRoutes.Welcome
                )
            },
        ) {
            Text(
                text = "Lets go!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
