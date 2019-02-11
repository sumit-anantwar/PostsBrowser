package com.sumitanantwar.mvp

import timber.log.Timber

/**
 * Created by Sumit Anantwar on 3/14/18.
 */
abstract class MvpPresenter<V: MvpViewInterface>(var view: V?) : MvpPresenterInterface<V> {

    init {
        this.onInit()
    }

    //======= Abstract =======
    abstract fun onInit()
    abstract fun onDestroy()


    //======= Lifecycle =======
    override fun destroy() {
        // Release the view
        view = null
        // Delegate onDestroy()
        onDestroy()
    }
}