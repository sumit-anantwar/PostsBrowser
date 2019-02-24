package com.sumitanantwar.postsbrowser.mobile.application.di

import android.app.Application
import com.sumitanantwar.postsbrowser.mobile.application.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule


@AppScope
@Component(
    modules = arrayOf (
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DataModule::class,
        DataStoreModule::class,
        UiModule::class
    )
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build() : AppComponent
    }

    fun inject(app: App)

}