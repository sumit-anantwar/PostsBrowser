package com.sumitanantwar.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class MvpController<V:MvpViewInterface, P:MvpPresenterInterface<V>> : BaseController {

    protected abstract val presenter    : P
    protected abstract val layoutId     : Int

    private lateinit var unbinder: Unbinder

    //======= Initializers =======
    protected constructor() : super()
    protected constructor(args: Bundle) : super(args)

    //======= Abstract =======
    protected abstract fun onViewBound(view: View)

    //======= Controller Lifecycle =======
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val v = inflater.inflate(layoutId, container, false)

        // ButterKnife Binder
        unbinder = ButterKnife.bind(this, v)

        // Delegate view bound
        onViewBound(v)

        // Return inflated view
        return v
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        // ButterKnife Unbinder
        unbinder.unbind()
        // Destroy the Presenter
        presenter.destroy()
    }
}