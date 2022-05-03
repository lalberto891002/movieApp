package com.mypc.movieapp.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.mypc.movieapp.model.Movie
import com.mypc.movieapp.model.getMovies

@Preview
@Composable
fun MovieRow(movie: Movie = getMovies()[0], onItemClicked:(String) -> Unit = {}){
    // val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }


    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        //.height(130.dp)
        .clickable {
            onItemClicked(movie.id)
            // context.startActivity(Intent(context, MainActivity::class.java))

        },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = RectangleShape, elevation = 4.dp,
            ) {
                /*Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Movie Image" )*/
               AsyncImage(model = ImageRequest.Builder(LocalContext.current).
                    data(movie.images[0]).
                    crossfade(true).
                    transformations(CircleCropTransformation()).
                    build(),
                   contentDescription = "poster")
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = movie.title,
                style = MaterialTheme.typography.h6)
                Text(text = "Director: ${movie.director}",
                style = MaterialTheme.typography.caption)
                Text(text = "Release: ${movie.year}",
                    style = MaterialTheme.typography.caption)
                AnimatedVisibility(visible = expanded){
                    Column {
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.DarkGray,
                            fontSize = 13.sp)){
                                append("Plot: ")
                            }

                            withStyle(style = SpanStyle(color = Color.DarkGray,
                                fontSize = 13.sp,
                            fontWeight = FontWeight.Bold)){
                                append(movie.plot)
                            }
                        },
                        modifier = Modifier.padding(6.dp))
                        Divider()
                        Text(text = "Actors:${movie.actors}", style = MaterialTheme.typography.caption, fontWeight = FontWeight.Bold)
                        Text(text = "Rate:${movie.rating}", style = MaterialTheme.typography.caption,fontWeight = FontWeight.Bold)
                    }
                }

                Icon(imageVector = if(!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = Color.DarkGray)

                
            }




        }



    }
}



