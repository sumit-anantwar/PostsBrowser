package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.nhaarman.mockitokotlin2.mock
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import com.sumitanantwar.postsbrowser.network.testdata.MockNetworkService
import dagger.Module
import dagger.Provides

@Module
object TestNetworkModule {

    @Provides
    @JvmStatic
    fun providesNetworkService(): NetworkService {
        return MockNetworkService()
    }

    @Provides
    @JvmStatic
    fun providesNetworkDataStore(networkService: NetworkService) : NetworkDataStore {
        return NetworkDataStoreImpl(networkService, PostsModelMapper())
    }

}