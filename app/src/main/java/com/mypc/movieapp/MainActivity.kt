package com.mypc.movieapp

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mypc.movieapp.model.Movie
import com.mypc.movieapp.model.getMovies
import com.mypc.movieapp.navigation.MovieNavigation
import com.mypc.movieapp.navigation.MovieScreens
import com.mypc.movieapp.ui.theme.MovieAppTheme
import com.mypc.movieapp.viewmodel.MovieViewModel
import com.mypc.movieapp.widgets.MovieRow
import java.io.Console
import java.lang.Math.log


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MovieNavigation()
            }
        }
    }
}

@Composable
fun MyApp(content:@Composable () -> Unit){
    MovieAppTheme {
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    navController: NavController,
    movieViewModel: MovieViewModel= viewModel()){
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
           itemsIndexed(items = movieViewModel.getAllMovies(),
           key = {
               index,item->
               item.hashCode()
           }
           ){ index,item->
               val dismissState = rememberDismissState(
                   confirmStateChange = {
                       if(it == DismissValue.DismissedToEnd){
                           movieViewModel.removeMovie(item)
                           Log.d("removed movie","Movie ${item.title} removed")
                           true
                       }else
                           false

                   }
               )
               SwipeToDismiss(
                   state = dismissState,
                   directions = setOf(DismissDirection.StartToEnd),
                   background = {
                       val direction = dismissState.dismissDirection?:return@SwipeToDismiss
                       val color by animateColorAsState(targetValue = when(dismissState.targetValue){
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Blue
                                DismissValue.DismissedToStart ->Color.LightGray
                                    })
                       val icon = when(direction){
                           DismissDirection.StartToEnd -> Icons.Default.Done
                           DismissDirection.EndToStart -> Icons.Default.Delete

                       }
                       val scale by animateFloatAsState(
                           targetValue =
                                if(dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)
                       val alignment = when(direction){
                           DismissDirection.EndToStart -> Alignment.CenterEnd
                           DismissDirection.StartToEnd -> Alignment.CenterStart
                       }
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(start = 12.dp, end = 12.dp),
                            contentAlignment = alignment){
                            Icon(icon, contentDescription ="Icon",Modifier.scale(scale))
                        }
                   },
                   dismissContent = {

                      MovieRow(movie = item){ movie ->
                           Log.d("Tag","MainContent: $movie")
                           navController.navigate(route = MovieScreens.DetailsScreen.name+"/$movie")
                       }

                   })

           }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MovieNavigation()
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Button(onClick = {
        context.startActivity(Intent(context, MainActivity::class.java))
    }) {
        Text(text = "Show List")
    }
}