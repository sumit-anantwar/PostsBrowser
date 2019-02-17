package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import com.sumitanantwar.mvp.MvpPresenterInterface
import com.sumitanantwar.mvp.MvpViewInterface
import com.sumitanantwar.postsbrowser.data.model.Post

object PostsListContract {

    interface View : MvpViewInterface {

        fun onFetchPosts(posts: List<Post>)
        fun onError(error: Throwable)
    }

    interface Presenter : MvpPresenterInterface<View> {

        fun fetchPosts()
    }

}