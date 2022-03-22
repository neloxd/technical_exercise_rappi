package com.jesusvilla.usecases

import com.jesusvilla.data.repository.MoviesRepository
import com.jesusvilla.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}