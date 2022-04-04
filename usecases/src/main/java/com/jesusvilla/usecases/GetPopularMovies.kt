package com.jesusvilla.usecases

import com.jesusvilla.data.repository.MoviesRepository
import com.jesusvilla.data.source.handler.Resource
import com.jesusvilla.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): Resource<List<Movie>> = moviesRepository.getPopularMovies()
}