package com.mustafacol.spacex.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.mustafacol.spacex.repository.SpacexRepository
import com.mustafacol.spacex.repository.SpacexRepositoryImpl
import com.mustafacol.spacex.ui.launch_details.LaunchDetailsViewModel
import com.mustafacol.spacex.ui.launch_list.LaunchListViewModel
import com.mustafacol.spacex.utils.Constant
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {

    single {
        OkHttpClient.Builder().build()
    }
    single {
        ApolloClient.Builder()
            .serverUrl(Constant.SERVERURL)
            .okHttpClient(get())
            .build()
    }
    single<SpacexRepository> {
        SpacexRepositoryImpl(get())
    }

    viewModel {
        LaunchDetailsViewModel()
        LaunchListViewModel()
    }
}