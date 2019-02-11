package com.sumitanantwar.postsbrowser.mobile.application.di

import com.sumitanantwar.postsbrowser.mobile.ui.activities.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity() : MainActivity

}