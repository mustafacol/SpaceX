package com.mustafacol.spacex.repository

import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.data.LaunchDetailsItem
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.data.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SpacexRepository {

    suspend fun getLaunches(offset: Int): Flow<NetworkResult<List<LaunchItem>>>
    suspend fun getLaunchDetails(launchId: String): Flow<NetworkResult<LaunchDetailsItem>>
}