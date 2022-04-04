package com.rappi.technicalexercise.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.jesusvilla.data.source.LocationDataSource
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {

    private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastRegion(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    val region = it.result.toRegion()
                    if(region?.isNotEmpty() == true)
                        continuation.resume(region)
                    else
                        continuation.resume(null)

                }
        }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}
