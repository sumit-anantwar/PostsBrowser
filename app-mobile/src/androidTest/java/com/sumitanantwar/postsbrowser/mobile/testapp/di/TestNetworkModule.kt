package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.nhaarman.mockitokotlin2.mock
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import dagger.Module
import dagger.Provides

@Module
object TestNetworkModule {

    @Provides
    @JvmStatic
    fun providesNetworkService(): NetworkService {
        return mock()
    }

    @Provides
    @JvmStatic
    fun providesNetworkDataStore() : NetworkDataStore {
        return mock()
    }

}