package com.jesusvilla.usecases

import com.jesusvilla.data.repository.MoviesRepository
import com.jesusvilla.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}