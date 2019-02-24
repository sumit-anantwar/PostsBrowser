package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.nhaarman.mockitokotlin2.mock
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import com.sumitanantwar.postsbrowser.mobile.scheduler.ImmediateSchedulerProvider
import com.sumitanantwar.postsbrowser.mobile.scheduler.RegularSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    fun bindsUiSchedulerProvider(): SchedulerProvider {
        return RegularSchedulerProvider()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun providesPostsRepository() : PostsRepository {
        return mock()
    }

}