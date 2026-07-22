package com.example.eplfixtures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eplfixtures.ui.screens.FixtureDetailScreen
import com.example.eplfixtures.ui.screens.FixtureListScreen
import com.example.eplfixtures.ui.theme.EPLFixturesTheme
import com.example.eplfixtures.ui.viewmodel.FixtureViewModel

/**
 * Навигация между экранами реализована через Navigation Compose
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EPLFixturesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    EplNavHost()
                }
            }
        }
    }
}

private object Routes {
    const val LIST = "fixtures_list"
    const val DETAIL = "fixture_detail/{matchNumber}"
    fun detail(matchNumber: Int) = "fixture_detail/$matchNumber"
    const val ARG_MATCH_NUMBER = "matchNumber"
}

@Composable
fun EplNavHost() {
    val navController = rememberNavController()
    val fixtureViewModel: FixtureViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            FixtureListScreen(
                viewModel = fixtureViewModel,
                onFixtureClick = { matchNumber ->
                    navController.navigate(Routes.detail(matchNumber))
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument(Routes.ARG_MATCH_NUMBER) { type = NavType.IntType })
        ) { backStackEntry ->
            val matchNumber = backStackEntry.arguments?.getInt(Routes.ARG_MATCH_NUMBER) ?: -1
            FixtureDetailScreen(
                matchNumber = matchNumber,
                viewModel = fixtureViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
