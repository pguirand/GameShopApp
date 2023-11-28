package com.pierretest.gameshopapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pierretest.gameshopapp.ui.theme.GameShopAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private var isDetailScreenVisible by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameShopAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }


}

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MainApp() {

    var displayBottomBar by remember { mutableStateOf(true) }
    var displayTopBar by remember { mutableStateOf(true) }
    val navController = rememberNavController()
    val gameViewModel : GameViewModel = hiltViewModel()

    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            "DetailGameScreen/{gameId}" -> {
                displayBottomBar = false
                displayTopBar = true
            }

            else -> {
                displayBottomBar = true
                displayTopBar = false
            }
        }

    }

    Scaffold(
        topBar = {
            if (displayTopBar) {
                TopAppBar(
                    title = {
                        Text(text = "")
                    },
                    actions = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                navController.navigateUp()
                                gameViewModel.clearGM()
                            })
                    }
                )
            }
        },
        bottomBar = {
            if (displayBottomBar) {
                BottomNavigationBar(navController)
            }
        },
        content = { paddingValues ->
            NavHost(
                navController,
                startDestination = "games",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("games") { GameListScreen(navController) }
                composable("categories") { CategoryListScreen(navController) }
                composable(
                    route = "CategoryDetailScreen/{catName}"
                ) {
                    val catName = it.arguments?.getString("catName") ?:""

                    if (catName.isNotEmpty()) {
                        CategoryDetailScreen(catName = catName, navController)
                    } else {
                        Toast.makeText(LocalContext.current, "Empty ID", Toast.LENGTH_SHORT).show()
                        return@composable
                    }
                }
                composable(
                    route = "DetailGameScreen/{gameId}",
                    arguments = listOf(
                        navArgument(name = "gameId") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->
                    val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
                    if (gameId == 0) {
                        Toast.makeText(LocalContext.current, "Empty ID", Toast.LENGTH_SHORT).show()
                        return@composable
                    }
//                    val viewModel: GameViewModel = hiltViewModel()
                    // Call a composable to show the detail page based on the gameId
                    // For example: DetailGameScreen(gameId)
//                    val game = gameViewModel.getGameById(gameId)
//                    val game1 = gameViewModel.getOneGameById(gameId)
                    DetailGameScreen(gameId)

                }
            }
        }
    )

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation {
        BottomNavigationItem(
            selected = navController.currentDestination?.route == "games",
            onClick = { navController.navigate("games") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Games") },
            label = { Text("Games") }
        )
        BottomNavigationItem(
            selected = navController.currentDestination?.route == "categories",
            onClick = { navController.navigate("categories") },
            icon = { Icon(Icons.Default.List, contentDescription = "Categories") },
            label = { Text("Categories") }
        )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GameShopAppTheme {
        Greeting("Android")
    }
}