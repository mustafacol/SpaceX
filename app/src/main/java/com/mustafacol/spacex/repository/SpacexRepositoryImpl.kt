package com.mustafacol.spacex.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.utils.Constant

class SpacexRepositoryImpl(
    private val apolloClient: ApolloClient
) : SpacexRepository {
    override suspend fun getLauches(offset: Int) {
        apolloClient.query(LaunchListQuery(
            limit = Optional.present(Constant.PAGE_LIMIT),
            offSet = Optional.present(offset),
            sort = Optional.present(Constant.SORT),
            order= Optional.present(Constant.ORDER)

        ))
    }

    override suspend fun getLaunchDetails(launchId: String) {
    }
}