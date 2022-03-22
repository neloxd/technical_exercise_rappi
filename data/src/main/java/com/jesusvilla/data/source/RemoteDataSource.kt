package com.jesusvilla.data.source

import com.jesusvilla.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}