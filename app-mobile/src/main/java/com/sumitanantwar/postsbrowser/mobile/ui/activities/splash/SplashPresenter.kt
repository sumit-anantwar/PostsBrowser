package com.sumitanantwar.postsbrowser.mobile.ui.activities.splash

import com.sumitanantwar.mvp.MvpPresenter
import javax.inject.Inject

class SplashPresenter (view: SplashContract.View) :
    MvpPresenter<SplashContract.View>(view),
    SplashContract.Presenter {

    //======= Presenter Lifecycle =======
    override fun onInit() {}

    override fun onDestroy() {}

    //======= Launch logic =======
    override fun launchApp() {
        view?.onLaunchMain()
    }
}