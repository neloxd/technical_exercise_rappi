package com.jesusvilla.usecases

import com.jesusvilla.data.repository.MoviesRepository
import com.jesusvilla.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}