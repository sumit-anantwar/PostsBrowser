package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.facebook.stetho.common.StringUtil
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sumitanantwar.library.animator.HeightProperty
import com.sumitanantwar.mvp.MvpController
import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.mobile.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import java.util.concurrent.TimeUnit
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

    @BindView(R.id.edit_text_userid)
    lateinit var editTextUserId: EditText

    @BindView(R.id.edit_text_title)
    lateinit var editTextTitle: EditText

    @BindView(R.id.edit_text_body)
    lateinit var editTextBody: EditText

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

    private val bag = CompositeDisposable()

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

        // Setup Edit Text Observables
        rxSetup()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return super.onCreateView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        bag.dispose()
        super.onDestroyView(view)
    }


    //======= Listeners =======
    @OnClick(R.id.button_filter)
    fun onClickFilterButton() {
        toggleFilterPanelVisibility()
    }


    //======= Private =======

    private fun rxSetup() {

        val userIdObservable = RxTextView.textChanges(editTextUserId)
            .map { it.toString() }
            .distinctUntilChanged()

        val titleObservable = RxTextView.textChanges(editTextTitle)
            .map { it.toString() }
            .distinctUntilChanged()

        val bodyObservable = RxTextView.textChanges(editTextBody)
            .map { it.toString() }
            .distinctUntilChanged()

        Observables.combineLatest(userIdObservable, titleObservable, bodyObservable)
            .debounce (300, TimeUnit.MILLISECONDS)
            .observeOn(schedulerProvider.ui())
            .subscribe {
                val userId  = it.first
                val title   = it.second
                val body    = it.third

                fetchPostsWithFilter(userId, title, body)

            }.addTo(bag)

    }

    private fun fetchPosts() {

        val userId  = editTextUserId.text.toString()
        val title   = editTextTitle.text.toString()
        val body    = editTextBody.text.toString()

        presenter.fetchPostsWithFilter(userId, title, body)
    }

    private fun fetchPostsWithFilter(userId: String, title: String, body: String) {
        swipeRefreshLayout.isRefreshing = true
        presenter.fetchPostsWithFilter(userId, title, body)
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
            duration = 300

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