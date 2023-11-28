package com.pierretest.gameshopapp.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.pierretest.gameshopapp.data.model.gamesModel.SingleGameModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailGameScreen(
    singleGameId: Int,
    viewModel : GameViewModel = hiltViewModel()
)
{
    viewModel.getOneGameById(singleGameId)
    val game by viewModel.OneGame.collectAsState()

    /*val singleGameModel by */

    if(game==null){
        Box(
            modifier= Modifier
                .fillMaxSize()
                .background(Color(200, 200, 200, 100))
        ){
            CircularProgressIndicator(
                modifier = Modifier
                    .background(Color(200, 200, 200, 100))
                    .align(Alignment.Center)
                ,
                color = Color.Red,
            )
        }
    }else{
        GameInfo(gameModel = game!!)

/*        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = game!!.title ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    },
                    // ... other top bar settings
                )
            },
            content = {
                // Rest of your content
                GameInfo(gameModel = game!!)
            }
        )*/

    }
}

//GameInfo(gameModel = game!!)


@Composable
fun PreviewGame() {
}

@Composable
fun GameInfo(gameModel: SingleGameModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${gameModel.title}",
                style = MaterialTheme.typography.headlineMedium,
//                maxLines = 2,
                textAlign = TextAlign.Center

            )
        }
        Row(modifier = Modifier
        ) {
            Image(
                modifier = Modifier
                    .size(250.dp)
                    .padding(2.dp),
                painter = rememberAsyncImagePainter(gameModel.thumbnail),
                contentDescription = "${gameModel.title}}"
            )
        }

        Row(
            modifier = Modifier
                .padding(2.dp),
            Arrangement.Center

        ) {
            Text(
                text = "${gameModel.shortDescription}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
            )
        }

        Separator1()
        Text(text = "Genre : ${gameModel.genre}")
        Text(text = "Platform : ${gameModel.platform}")
        Text(text = "Released Date : ${gameModel.releaseDate}")
        Separator1()

        val screenshotsList = gameModel.screenshots?.map { it?.image }
        if(screenshotsList==null){
            Box(
                modifier= Modifier
                    .fillMaxSize()
                    .background(Color(200, 200, 200, 100))
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(Color(200, 200, 200, 100))
                        .align(Alignment.Center)
                    ,
                    color = Color.Red,
                )
            }
        } else {
            screenshotsList?.let { ImageList(images = it) }
        }
    }

}

@Composable
fun Separator1() {
    Spacer(modifier = Modifier.height(6.dp))
    Divider(
        modifier = Modifier
            .padding(20.dp)
            .height(2.dp)
    )
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun ImageList(images: List<String?>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        content = {
            items(images) { imageUrl ->

                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(horizontal = 8.dp),
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Screenshot"
                )
            }
        }
    )
}








