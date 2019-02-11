package com.sumitanantwar.postsbrowser.mobile.ui.activities.splash

import com.sumitanantwar.mvp.MvpPresenterInterface
import com.sumitanantwar.mvp.MvpViewInterface


object SplashContract {

    interface View : MvpViewInterface {

        /** Launch the [MainActivity] */
        fun onLaunchMain()

    }

    interface Presenter : MvpPresenterInterface<View> {

        /** Process the Launch check and Launch the App */
        fun launchApp()
    }
}