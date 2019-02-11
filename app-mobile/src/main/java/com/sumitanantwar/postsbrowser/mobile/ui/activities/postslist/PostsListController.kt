package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import butterknife.BindView
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.mvp.MvpController
import com.sumitanantwar.postsbrowser.data.PostsRepository
import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import javax.inject.Inject

class PostsListController @Inject constructor(

) : MvpController<PostsListContract.View, PostsListContract.Presenter>(),
    PostsListContract.View {

    //======= ButterKnife Binders =======
    @BindView(R.id.recycler_posts)
    lateinit var postsRecyclerView: RecyclerView

    @BindView(R.id.progress)
    lateinit var progressBar: ProgressBar


    //======= Injections =======
    @Inject lateinit var postsRepository: PostsRepository
    @Inject lateinit var postsListAdapter: PostListAdapter
    @Inject lateinit var schedulerProvider: SchedulerProvider


    override lateinit var presenter: PostsListPresenter
    override val layoutId: Int = R.layout.postslist_controller

    //======= Controller Lifecycle =======
    override fun onViewBound(view: View) {

        presenter = PostsListPresenter(postsRepository, schedulerProvider, this)

        // Setup RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        postsRecyclerView.adapter       = postsListAdapter

        val dividerDecoration = DividerItemDecoration(this.applicationContext, DividerItemDecoration.VERTICAL)
        dividerDecoration.setDrawable(ContextCompat.getDrawable(this.applicationContext!!, R.drawable.divider_shape)!!)
        postsRecyclerView.addItemDecoration(dividerDecoration)

        // Fetch Posts
        presenter.fetchPosts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return super.onCreateView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }

    //======= PostsListContract.View =======
    override fun onFetchPosts(posts: List<Post>) {
        progressBar.visibility = View.GONE
        postsListAdapter.updatePosts(posts)
    }
}