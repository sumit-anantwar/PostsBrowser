package com.sumitanantwar.postsbrowser.mobile.ui.activities.splash

import android.os.Bundle
import android.os.Handler
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.mvp.MvpActivity
import com.sumitanantwar.postsbrowser.mobile.ui.activities.main.MainActivityIntent
import timber.log.Timber

class SplashActivity : MvpActivity<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    // ======= Private Fields =======
    override lateinit var presenter: SplashPresenter

    private var handler: Handler? = null

    // ======= Activity Lifecycle =======
    override fun onCreate(savedInstanceState: Bundle?) {
        // Super
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // Initialize the Presenter
        presenter = SplashPresenter(this)

        // Delay App Launch by 2 sec.
        handler = Handler()
    }

    override fun onStart() {
        super.onStart()

        handler?.postDelayed({
            presenter.launchApp()
        }, 2000)
    }

    override fun onLaunchMain() {
        Timber.d("Launching Main Activity")
        val intent = MainActivityIntent()
        startActivity(intent)
    }
}