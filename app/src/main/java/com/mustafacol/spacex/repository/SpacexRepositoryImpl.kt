package com.mustafacol.spacex.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.mustafacol.spacex.LaunchDetailsQuery
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.data.NetworkResult
import com.mustafacol.spacex.utils.Constant
import com.mustafacol.spacex.utils.toApiModel
import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.data.LaunchDetailsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SpacexRepositoryImpl(
    private val apolloClient: ApolloClient
) : SpacexRepository {
    override suspend fun getLaunches(offset: Int): Flow<NetworkResult<List<LaunchItem>>> {
        return flow {
            val response = apolloClient.query(
                LaunchListQuery(
                    limit = Optional.present(Constant.PAGE_LIMIT),
                    offSet = Optional.present(offset),
                    sort = Optional.present(Constant.SORT),
                    order = Optional.present(Constant.ORDER)
                )
            ).execute()
            if (response.hasErrors()) {
                emit(
                    NetworkResult.Failure(
                        Exception(
                            response.errors?.toString() ?: "Someting went wrong..."
                        )
                    )
                )
                return@flow
            }
            response.data?.launchesPast?.also { launchesPasts ->
                launchesPasts.mapNotNull {
                    it?.toApiModel()
                }.apply {
                    emit(NetworkResult.Success(this))
                }
            }
        }.flowOn(
            Dispatchers.IO
        )
    }

    override suspend fun getLaunchDetails(launchId: String): Flow<NetworkResult<LaunchDetailsItem>> {
        return flow<NetworkResult<LaunchDetailsItem>> {
            val response = apolloClient.query(
                LaunchDetailsQuery(
                    id = launchId
                )
            ).execute()
            if (response.hasErrors()) {
                emit(
                    NetworkResult.Failure(
                        Exception(
                            response.errors?.toString() ?: "Someting went wrong..."
                        )
                    )
                )
                return@flow
            }

            emit(NetworkResult.Success(response.data?.launch?.toApiModel()!!))

        }.flowOn(Dispatchers.IO)
    }
}