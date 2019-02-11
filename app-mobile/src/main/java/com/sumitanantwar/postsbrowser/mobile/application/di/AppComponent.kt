package com.sumitanantwar.postsbrowser.mobile.application.di

import android.app.Application
import android.content.Context
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.mobile.application.App
import dagger.BindsInstance
import dagger.Component


@AppScope
@Component(modules = arrayOf(
    AppModule::class,
    UiModule::class,
    NetworkModule::class,
    DataStoreModule::class)
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicatopm(application: Application): Builder

        fun build() : AppComponent
    }

    fun inject(app: App)

}