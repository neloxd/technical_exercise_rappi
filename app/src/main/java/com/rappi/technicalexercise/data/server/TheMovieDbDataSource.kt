package com.rappi.technicalexercise.data.server

import com.jesusvilla.data.source.RemoteDataSource
import com.jesusvilla.domain.Movie
import com.rappi.technicalexercise.data.toDomainMovie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, region,"es")
            .results
            .map {
                it.toDomainMovie()
            }
}