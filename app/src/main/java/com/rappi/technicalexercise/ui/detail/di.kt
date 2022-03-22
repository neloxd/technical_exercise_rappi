package com.rappi.technicalexercise.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.jesusvilla.data.repository.MoviesRepository
import com.jesusvilla.usecases.FindMovieById
import com.jesusvilla.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.lang.IllegalStateException
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DetailActivityModule {

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)

    @Provides
    @Named("movieId")
    fun movieIdProvider(stateHandle: SavedStateHandle): Int =
        stateHandle.get<Int>(DetailActivity.MOVIE)
            ?: throw IllegalStateException("Movie Id not found in the state handle")
}