package com.rappi.technicalexercise.di


import android.app.Application
import androidx.room.Room
import com.jesusvilla.data.repository.PermissionChecker
import com.jesusvilla.data.source.LocalDataSource
import com.jesusvilla.data.source.LocationDataSource
import com.jesusvilla.data.source.RemoteDataSource
import com.rappi.technicalexercise.R
import com.rappi.technicalexercise.data.AndroidPermissionChecker
import com.rappi.technicalexercise.data.PlayServicesLocationDataSource
import com.rappi.technicalexercise.data.database.MovieDatabase
import com.rappi.technicalexercise.data.database.RoomDataSource
import com.rappi.technicalexercise.data.server.TheMovieDbDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}