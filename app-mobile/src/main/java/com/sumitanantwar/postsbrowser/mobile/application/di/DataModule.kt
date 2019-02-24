package com.sumitanantwar.postsbrowser.mobile.application.di

import com.sumitanantwar.postsbrowser.data.PostsRepositoryImpl
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import com.sumitanantwar.postsbrowser.mobile.scheduler.RegularSchedulerProvider
import dagger.Binds
import dagger.Module


@Module
abstract class DataModule {

    @Binds
    @AppScope
    abstract fun bindsUiSchedulerProvider(regularSchedulerProvider: RegularSchedulerProvider): SchedulerProvider

    @Binds
    @AppScope
    abstract fun providesPostsRepository(postsRepository: PostsRepositoryImpl) : PostsRepository

}