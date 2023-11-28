package com.pierretest.gameshopapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameListScreen(navController : NavHostController, viewModel : GameViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.getAllGames()
    }

    val gameList by viewModel.listAllGames.collectAsState()

    if (gameList.isNotEmpty()) {
        ListItems(gameList = gameList, navController = navController)
    }

}

@Composable
fun EmptyList(msg : String = "No Games Available", category:String="") {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (category=="") msg else "$msg : $category",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItems(gameList : List<SingleGameModel>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    )
    {

        stickyHeader(
            key=null,
            contentType = null,
            content = {
                Row (modifier = Modifier
                    .background(Color(73, 65, 65, 100))
                    .padding(8.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Total : ${gameList.size} games",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
            }
        )

        items(gameList) {game ->
            GameItem(singleGameModel = game) {
                navController.navigate("DetailGameScreen/${game.id}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameItem(singleGameModel : SingleGameModel, onItemClick : () -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Surface(
            onClick = onItemClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(200, 220, 240, 255))
            ) {
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .fillMaxHeight()
                        .weight(0.4f),
                    painter = rememberAsyncImagePainter(model = singleGameModel.thumbnail),
                    contentDescription = "thumbnail"
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .weight(0.6f)
                ) {
                    Text(
                        text = singleGameModel.title.toString().trim(),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = singleGameModel.shortDescription.toString(),
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}