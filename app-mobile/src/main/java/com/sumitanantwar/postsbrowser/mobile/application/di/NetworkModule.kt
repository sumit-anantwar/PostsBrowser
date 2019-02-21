package com.sumitanantwar.postsbrowser.mobile.application.di

import android.content.Context
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import com.sumitanantwar.postsbrowser.network.service.NetworkServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

@Module
abstract class NetworkModule {

    @Module
    companion object {

        @Provides
        @AppScope
        @JvmStatic
        fun providesNetworkService(context: Context): NetworkService {

            val cacheDir = File(context.cacheDir, "okhttp-cache")
            val cache = Cache(cacheDir, 10 * 1000 * 1000)

            val okHttpClientBuilder = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .cache(cache)

            return NetworkServiceFactory.makeNetworkService(BuildConfig.DEBUG, okHttpClientBuilder)
        }
    }


    @Binds
    @AppScope
    abstract fun providesNetworkDataStore(networkDataStore: NetworkDataStoreImpl) : NetworkDataStore
}