package com.pierretest.gameshopapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


@Composable
fun CategoryListScreen(navController: NavHostController, viewModel: CategoryViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }

    val catlist by viewModel.listAllCategories.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Two columns
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    )
    {
        items(catlist) {cat ->
            CategoryItem(categoryModel = cat) {
                navController.navigate("CategoryDetailScreen/${cat}")
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(categoryModel:String, onItemClick : () -> Unit) {

    val fadingBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(150, 150, 255, 200),
            Color(200, 200, 255, 100),

//            Color.Transparent

        ),
        startX = 0f,
//        endX = 150f
    )

    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
//            .fillMaxWidth()
            .height(110.dp)
    ) {

        Surface(
            onClick = onItemClick,
            modifier = Modifier
                .padding(4.dp)
//                .background(fadingBrush)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .background(fadingBrush),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = categoryModel,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
    }
}
