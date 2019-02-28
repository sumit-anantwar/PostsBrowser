package com.sumitanantwar.mvp

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Sumit Anantwar on 3/14/18.
 */
@Suppress("UNCHECKED_CAST")
abstract class MvpActivity<V:MvpViewInterface, P:MvpPresenterInterface<V>> : BaseActivity() {

    protected abstract val presenter: P



    override fun onDestroy() {

        // Destroy the Presenter
        presenter.destroy()

        super.onDestroy()
    }
}
