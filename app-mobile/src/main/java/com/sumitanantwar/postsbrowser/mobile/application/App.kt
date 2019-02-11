package com.sumitanantwar.postsbrowser.mobile.application

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.sumitanantwar.postsbrowser.mobile.application.di.AppComponent
import com.sumitanantwar.postsbrowser.mobile.application.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    //======= Activity Injector =======
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    //======= Static Accessor =======
    companion object {
        fun get(activity: Activity) : App {
            return activity.application as App
        }
    }


    // ======= Application Lifecycle =======
    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .applicatopm(this)
            .build()
            .inject(this)


        // Initialize Crashlytics
        Fabric.with(this, Crashlytics())

        // Initialize Stetho
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build())

        // Plant a Timber Debug Tree
        Timber.plant(Timber.DebugTree())
    }

}