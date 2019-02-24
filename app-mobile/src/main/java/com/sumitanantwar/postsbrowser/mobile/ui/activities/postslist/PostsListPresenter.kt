package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import com.sumitanantwar.mvp.MvpPresenter

import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class PostsListPresenter(
    private val postsRepository: PostsRepository,
    private val schedulerProvider: SchedulerProvider,
    view: PostsListContract.View
) : MvpPresenter<PostsListContract.View>(view),
    PostsListContract.Presenter {

    val bag = CompositeDisposable()

    //======= Presenter Lifecycle =======
    override fun onInit() {

    }

    override fun onDestroy() {
        bag.dispose()
    }

    //======= PostsListContract.Presenter =======

    override fun fetchPostsWithFilter(userId: String, title: String, body: String) {

        postsRepository.fetchPostsWithFilter(userId, title, body)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({

                view?.onFetchPosts(it)

            }, {

                view?.onError(it)

            }).addTo(bag)
    }
}