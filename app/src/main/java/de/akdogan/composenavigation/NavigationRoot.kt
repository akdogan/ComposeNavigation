package de.akdogan.composenavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.akdogan.composenavigation.ui.detailscreen.DetailScreen
import de.akdogan.composenavigation.ui.detailscreen.DetailScreenViewModel
import de.akdogan.composenavigation.ui.listscreen.ListScreen
import de.akdogan.composenavigation.ui.listscreen.ListScreenViewModel
import de.akdogan.composenavigation.ui.welcomescreen.WelcomeScreen
import kotlinx.serialization.Serializable

sealed interface NavigationRoutes {

    @Serializable
    data object Welcome : NavigationRoutes

    @Serializable
    data object List : NavigationRoutes

    @Serializable
    data class Detail(val id: Int) : NavigationRoutes
}

data class ScreenConfiguration(
    val showUpArrow: Boolean,
    val title: String // todo how to make this dynamic?
    // todo maybe add menu items
) {
    companion object {
        val UNKNOWN = ScreenConfiguration(showUpArrow = false, "")
        val WELCOME_CONFIG = ScreenConfiguration(showUpArrow = false, "WELCOME")
        val LIST_CONFIG = ScreenConfiguration(showUpArrow = false, "CHARACTERS")
        val DETAIL_CONFIG = ScreenConfiguration(showUpArrow = true, "DETAILS")
    }
}

interface Navigator {
    /**
     * @param route The destination that should be navigated to
     * @param popRoute if set, will pop up to the specified route including the specified route
     */
    fun navigateToRoute(route: NavigationRoutes, popUpToRouteInclusive: NavigationRoutes? = null)
    fun navigateUp()
}

class DefaultNavigator(private val navHostController: NavHostController) : Navigator {
    override fun navigateToRoute(route: NavigationRoutes, popUpToRouteInclusive: NavigationRoutes?) {
        navHostController.navigate(route = route) {
            if (popUpToRouteInclusive != null) popUpTo(popUpToRouteInclusive) { inclusive = true }
        }
    }

    override fun navigateUp() {
        navHostController.navigateUp()
    }
}

// using compositionLocalProvider to inject the Navigator into the composable tree
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("Navigator was not configured yet!")
}

@Composable
fun NavigationComponent(
    navHostController: NavHostController = rememberNavController(),
    updateScreenConfig: (ScreenConfiguration) -> Unit
) {
    val navigator = DefaultNavigator(navHostController)
    CompositionLocalProvider(value = LocalNavigator provides navigator) {

        NavHost(navController = navHostController, startDestination = NavigationRoutes.Welcome) {
            composable<NavigationRoutes.Welcome> {
                WelcomeScreen()
                updateScreenConfig(ScreenConfiguration.WELCOME_CONFIG)
            }
            composable<NavigationRoutes.List> {
                // todo use injection for viewmodels, right now VMs are scoped to activity
                val listViewModel: ListScreenViewModel = viewModel()

                ListScreen(viewModel = listViewModel)
                updateScreenConfig(ScreenConfiguration.LIST_CONFIG)
            }
            composable<NavigationRoutes.Detail> {
                val detailViewModel: DetailScreenViewModel = viewModel()
                val detailRoute: NavigationRoutes.Detail = it.toRoute()

                DetailScreen(viewModel = detailViewModel, itemId = detailRoute.id)
                updateScreenConfig(ScreenConfiguration.DETAIL_CONFIG)
            }
        }
    }
}
