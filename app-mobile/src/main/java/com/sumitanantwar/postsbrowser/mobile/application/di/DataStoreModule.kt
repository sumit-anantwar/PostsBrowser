package com.sumitanantwar.postsbrowser.mobile.application.di

import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.data.store.PostsDataStore
import com.sumitanantwar.postsbrowser.datastore.PostsDataStoreImpl
import com.sumitanantwar.postsbrowser.mobile.scheduler.RegularSchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class DataStoreModule {

    @Binds
    @AppScope
    abstract fun providesPostsDataStore(postsDataStore: PostsDataStoreImpl): PostsDataStore
}