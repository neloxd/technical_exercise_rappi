package com.rappi.technicalexercise.di

import com.jesusvilla.data.repository.MoviesRepository
import com.jesusvilla.data.repository.PermissionChecker
import com.jesusvilla.data.repository.RegionRepository
import com.jesusvilla.data.source.LocalDataSource
import com.jesusvilla.data.source.LocationDataSource
import com.jesusvilla.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
}