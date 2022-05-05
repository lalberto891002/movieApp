package com.mypc.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mypc.movieapp.model.Movie
import androidx.compose.runtime.mutableStateListOf
import com.mypc.movieapp.model.getMovies

class MovieViewModel:ViewModel() {
    var movieList = mutableStateListOf<Movie>()
    init{
        movieList.addAll(getMovies())

    }

    fun addMovie(movie:Movie){
        movieList.add(movie)
    }

    fun removeMovie(movie:Movie){
        movieList.remove(movie)
    }

    fun getAllMovies():List<Movie>{
        return movieList
    }




}