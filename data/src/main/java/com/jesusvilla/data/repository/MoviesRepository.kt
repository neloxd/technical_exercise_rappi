package com.jesusvilla.data.repository

import com.jesusvilla.data.source.LocalDataSource
import com.jesusvilla.data.source.RemoteDataSource
import com.jesusvilla.data.source.handler.Resource
import com.jesusvilla.data.source.handler.ResponseHandler
import com.jesusvilla.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String,
    private val responseHandler: ResponseHandler
) {

    suspend fun getPopularMovies(): Resource<List<Movie>> {
        return try {
            if (localDataSource.isEmpty()) {
                val movies =
                    remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
                localDataSource.saveMovies(movies)
            }
            responseHandler.handleSuccess(localDataSource.getPopularMovies())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}