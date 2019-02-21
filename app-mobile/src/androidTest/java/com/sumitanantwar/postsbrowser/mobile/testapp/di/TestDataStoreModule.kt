package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.nhaarman.mockitokotlin2.mock
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.data.store.PostsDataStore
import com.sumitanantwar.postsbrowser.datastore.PostsDataStoreImpl
import com.sumitanantwar.postsbrowser.mobile.scheduler.ImmediateSchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object TestDataStoreModule {

    @Provides
    @JvmStatic
    fun providesPosatsDataStore(): PostsDataStore {
        return mock()
    }

}