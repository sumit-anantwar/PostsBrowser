package com.sumitanantwar.postsbrowser.mobile.ui.activities.postdetails

import com.sumitanantwar.mvp.MvpPresenter


class PostDetailsPresenter (
    view: PostDetailsContract.View
) : MvpPresenter<PostDetailsContract.View>(view),
    PostDetailsContract.Presenter {


    override fun onInit() {

    }

    override fun onDestroy() {

    }

}