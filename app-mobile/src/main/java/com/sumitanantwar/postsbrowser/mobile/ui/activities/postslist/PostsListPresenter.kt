package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import com.sumitanantwar.mvp.MvpPresenter
import com.sumitanantwar.postsbrowser.data.PostsRepository
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

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
    override fun fetchPosts() {
        postsRepository.fetchAllPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({

                view?.onFetchPosts(it)

            }, {

                Timber.d(it.localizedMessage)
            }).addTo(bag)
    }
}