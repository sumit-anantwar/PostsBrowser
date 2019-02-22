package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.OnClick
import com.sumitanantwar.library.animator.HeightProperty
import com.sumitanantwar.mvp.MvpController
import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.mobile.R
import timber.log.Timber
import javax.inject.Inject

class PostsListController @Inject constructor() : MvpController<PostsListContract.View, PostsListContract.Presenter>(),
    PostsListContract.View {

    //======= ButterKnife Binders =======

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.filter_container)
    lateinit var filterContiner: ViewGroup

    @BindView(R.id.filter_bar)
    lateinit var filterBar: ViewGroup

    @BindView(R.id.layout_filter)
    lateinit var filterPanel: ViewGroup

    @BindView(R.id.recycler_posts)
    lateinit var postsRecyclerView: RecyclerView

    @BindView(R.id.swiperefresh_posts)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    //======= Injections =======
    @Inject
    lateinit var postsRepository: PostsRepository
    @Inject
    lateinit var postsListAdapter: PostListAdapter
    @Inject
    lateinit var schedulerProvider: SchedulerProvider


    override lateinit var presenter: PostsListPresenter
    override val layoutId: Int = R.layout.postslist_controller


    //======= Controller Lifecycle =======
    override fun onViewBound(view: View) {

        presenter = PostsListPresenter(postsRepository, schedulerProvider, this)

        // Setup RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        postsRecyclerView.adapter = postsListAdapter

        // Swipe Refresh Listener
        swipeRefreshLayout.setOnRefreshListener {
            fetchPosts()
        }

        val dividerDecoration = DividerItemDecoration(this.applicationContext, DividerItemDecoration.VERTICAL)
        dividerDecoration.setDrawable(ContextCompat.getDrawable(this.applicationContext!!, R.drawable.divider_shape)!!)
        postsRecyclerView.addItemDecoration(dividerDecoration)

        // Fetch Posts
        fetchPosts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return super.onCreateView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }


    //======= Listeners =======
    @OnClick(R.id.button_filter)
    fun onClickFilterButton() {
        toggleFilterPanelVisibility()
    }


    //======= Private =======
    private fun fetchPosts() {
        swipeRefreshLayout.isRefreshing = true
        presenter.fetchPosts()
    }

    /** Toggles the Visibility of the Filter Panel */
    private fun toggleFilterPanelVisibility() {

        // Check the currnt state of the Filter Panel
        val isFilterPanelHidden = (filterPanel.visibility == View.INVISIBLE)

        // Make the panel visible, so that the animation can be seen
        filterPanel.visibility = View.VISIBLE

        // Store the current dimensions of the views to be animated
        val filterContainerHeight = filterContiner.height.toFloat()
        val filterBarHeight = filterBar.height.toFloat()
        val filterPanelHeight = filterPanel.height.toFloat()

        // Calculate the new positions
        var filterPanelY = filterBarHeight
        var newFilterContainerHeight = filterBarHeight + filterPanelHeight
        if (!isFilterPanelHidden) {
            filterPanelY = -filterPanelHeight
            newFilterContainerHeight = filterBarHeight
        }

        // Set the view layer types to Hardware for smoother animation
        filterPanel.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        filterContiner.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        // Create an animator set and play the animations together
        AnimatorSet().apply {
            duration = 3000

            playTogether(
                ObjectAnimator.ofFloat(filterPanel, "y", filterPanelY),
                ObjectAnimator.ofFloat(
                    filterContiner,
                    HeightProperty(),
                    filterContainerHeight,
                    newFilterContainerHeight
                )
            )

            addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        filterPanel.setLayerType(View.LAYER_TYPE_NONE, null)
                        filterContiner.setLayerType(View.LAYER_TYPE_NONE, null)

                        filterPanel.visibility = if (isFilterPanelHidden) View.VISIBLE else View.INVISIBLE
                    }
                }
            )
        }.start()
    }


    //======= PostsListContract.View =======
    override fun onFetchPosts(posts: List<Post>) {
        swipeRefreshLayout.isRefreshing = false
        postsListAdapter.updatePosts(posts)
    }

    override fun onError(error: Throwable) {
        swipeRefreshLayout.isRefreshing = false
        Timber.d("Error : ${error.localizedMessage}")
    }
}