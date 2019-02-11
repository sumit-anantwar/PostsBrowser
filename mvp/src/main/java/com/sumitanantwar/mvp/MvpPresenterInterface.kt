package com.sumitanantwar.mvp

/**
 * Created by Sumit Anantwar on 3/13/18.
 */
interface MvpPresenterInterface<in V : MvpViewInterface> {
    fun destroy()
}