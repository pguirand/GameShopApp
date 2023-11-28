package com.pierretest.gameshopapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel

@Composable
fun CategoryDetailScreen(
    catName: String,
    navController: NavHostController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    viewModel.getSingleCategory(catName)
    val category by viewModel.singleCategory.collectAsState()
    val isLoaded by viewModel.isLoaded.collectAsState(initial = false)



    if (isLoaded) {
        if (category.isNotEmpty()) {
            ListItems(gameList = category, navController = navController)
        } else {

            EmptyList("No Games in the category", category = catName)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(200, 200, 200, 100))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .background(Color(200, 200, 200, 100))
                    .align(Alignment.Center),
                color = Color.Red,
            )
        }
    }

}


