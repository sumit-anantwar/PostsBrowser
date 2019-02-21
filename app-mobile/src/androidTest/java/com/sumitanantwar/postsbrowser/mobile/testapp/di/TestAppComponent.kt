package com.sumitanantwar.postsbrowser.mobile.testapp.di

import android.app.Application
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import com.sumitanantwar.postsbrowser.mobile.application.di.UiModule
import com.sumitanantwar.postsbrowser.mobile.testapp.TestApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        TestAppModule::class,
        TestDataModule::class,
        TestDataStoreModule::class,
        TestNetworkModule::class,
        UiModule::class
    )
)
interface TestAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestAppComponent.Builder

        fun build() : TestAppComponent
    }

    fun inject(app: TestApp)


    //======= Accessors for Testing =======
    // These will be resolved by Dagger using Static references due to the @JvmStatic annotation in the Modules
    fun postsRepository() : PostsRepository

}