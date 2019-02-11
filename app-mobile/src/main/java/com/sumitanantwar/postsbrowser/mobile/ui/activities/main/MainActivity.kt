package com.sumitanantwar.postsbrowser.mobile.ui.activities.main

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import butterknife.BindView
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.mvp.BaseActivity
import com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist.PostsListController
import dagger.android.AndroidInjection
import javax.inject.Inject

// ======= Intent =======
fun Context.MainActivityIntent() : Intent {
    return Intent(this, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}


class MainActivity : BaseActivity() {

    // Butterknife Bindings
    @BindView(R.id.controller_container)
    lateinit var container: ViewGroup

    // Injection
    @Inject lateinit var postsListController: PostsListController

    // Conductor Router
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(postsListController))
        }
    }


}
