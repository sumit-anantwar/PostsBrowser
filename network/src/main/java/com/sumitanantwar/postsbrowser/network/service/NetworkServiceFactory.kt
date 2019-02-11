package com.sumitanantwar.postsbrowser.network.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceFactory {

    val stagingUrl = "https://jsonplaceholder.typicode.com"
    val productionUrl = "https://jsonplaceholder.typicode.com"

    fun baseUrl(isDebug: Boolean) =
        if (isDebug) {
            stagingUrl
        } else {
            productionUrl
        }


    fun makeNetworkService(isDebug: Boolean,
                           okHttpClientBuilder: OkHttpClient.Builder
    ) : NetworkService {

        val baseUrl = baseUrl(isDebug)
        val loggingInterceptor  = makeLoggingInterceptor(isDebug)
        val headerInterceptor   = makeHeaderInterceptor()
        val callAdapterFactory  = RxJava2CallAdapterFactory.create()

        val okHttpClient = okHttpClientBuilder
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()

        return NetworkServiceFactory.makeNetworkService(baseUrl, okHttpClient, callAdapterFactory)
    }


    private fun makeNetworkService(baseUrl: String,
                                   okHttpClient: OkHttpClient,
                                   callAdapterFactory: RxJava2CallAdapterFactory
    ) : NetworkService {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()

        return retrofit.create(NetworkService::class.java)
    }


    private fun makeLoggingInterceptor(isDebug: Boolean) : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    fun makeHeaderInterceptor() : Interceptor {
        return Interceptor {
            it.proceed(it.request().newBuilder()
                .addHeader("Content-Type", "application/json").build())
        }
    }

}