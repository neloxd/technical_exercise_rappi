package com.jesusvilla.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}