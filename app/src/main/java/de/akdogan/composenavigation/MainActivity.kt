package de.akdogan.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import de.akdogan.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {

    private val screenConfiguration: MutableState<ScreenConfiguration> = mutableStateOf(ScreenConfiguration.UNKNOWN)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // todo extract
            ComposeNavigationTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(screenConfiguration.value.title) },
                            navigationIcon = {
                                if (screenConfiguration.value.showUpArrow) {
                                    NavigationButton(
                                        modifier = Modifier.padding(top = 1.dp, start = 6.dp, end = 6.dp),
                                        onClick = { navHostController.navigateUp() }
                                    )
                                }
                            }
                        )
                    },
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(
                                    start = innerPadding.calculateStartPadding(layoutDirection = LayoutDirection.Ltr),
                                    end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Ltr),
                                    top = innerPadding.calculateTopPadding(),
                                    bottom = innerPadding.calculateBottomPadding(),
                                )
                                .fillMaxSize()
                        ) {
                            NavigationComponent(
                                navHostController = navHostController,
                                updateScreenConfig = ::updateScreenConfig
                            )
                        }
                    }
                )
            }
        }
    }

    private fun updateScreenConfig(config: ScreenConfiguration) {
        screenConfiguration.value = config
    }
}

@Composable
private fun NavigationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(8.dp),
        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
        contentDescription = ""
    )
}
