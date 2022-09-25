package com.mustafacol.spacex.repository

interface SpacexRepository {

    suspend fun getLauches(offset: Int)
    suspend fun getLaunchDetails(launchId: String)
}