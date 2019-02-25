package com.sumitanantwar.postsbrowser.mobile.postslist

import com.nhaarman.mockitokotlin2.*
import com.sumitanantwar.postsbrowser.data.PostsRepositoryImpl
import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.store.DataStoreFactory
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.data.store.PostsDataStore
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import com.sumitanantwar.postsbrowser.data.scheduler.SchedulerProvider
import com.sumitanantwar.postsbrowser.datastore.PostsDataStoreImpl
import com.sumitanantwar.postsbrowser.mobile.scheduler.ImmediateSchedulerProvider
import com.sumitanantwar.postsbrowser.mobile.scheduler.RegularSchedulerProvider
import com.sumitanantwar.postsbrowser.mobile.testdata.TestDataFactory
import com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist.PostsListContract
import com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist.PostsListPresenter
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.model.PostModel
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as _is


class PostsListPresenterTests {

    // TestDataFactory
    private val testDataFactory = TestDataFactory()

    private lateinit var mockView: PostsListContract.View
    private lateinit var postsRepository: PostsRepository
    private lateinit var dataStoreFactory: DataStoreFactory
    private lateinit var networkDataStore: NetworkDataStore
    private lateinit var postDataStore: PostsDataStore

    // Scheduler
    private val schedulerProvider = ImmediateSchedulerProvider()

    // SUT
    private lateinit var PostsListPresenter_SUT: PostsListPresenter

    @Before
    fun setup() {

        //=== MOCK ===
        mockView = mock { }
    }

    @After
    fun tearDown() {

    }


    @Test
    fun test_FetchPosts_Returns_ListOfPosts() {


        //=== Mock ===
        val mockNetworkService = NetworkService_TD()

        prepareProperties(mockNetworkService)

        //=== GIVEN THAT ===
        PostsListPresenter_SUT = PostsListPresenter(postsRepository, schedulerProvider, mockView)

        //=== WHEN ===
        PostsListPresenter_SUT.fetchPostsWithFilter("", "", "")

        //=== THEN ===
        assertThat(mockNetworkService.fetchPostCount, _is(1))

        verify(mockView, times(1)).onFetchPosts(check {
            assertThat(it.count(), _is(10))
            assertThat(it.first().id, _is(1))
            assertThat(it.last().id, _is(10))
        })

    }

    @Test
    fun test_FetchPostsFilteredByUserId_Returns_FilteredListOfPosts() {

        testFilteredPostFetch("2")

    }

    @Test
    fun test_FetchPostsFilteredByUserIdAndTitle_Returns_FilteredListOfPosts() {

        testFilteredPostFetch("3", "est")

    }

    @Test
    fun test_FetchPostsFilteredByUserIdAndBody_Returns_FilteredListOfPosts() {

        testFilteredPostFetch("4", body = "sed")

    }

    @Test
    fun test_FetchPostsFilteredByBody_Returns_FilteredListOfPosts() {

        testFilteredPostFetch( body = "sed")

    }

    @Test
    fun test_FetchPostsFilteredByTitle_Returns_FilteredListOfPosts() {

        testFilteredPostFetch( title = "est")

    }

    private fun testFilteredPostFetch(userId: String = "", title: String = "", body: String = "") {

        // Internal function to assert valid post
        fun Post.assertValid() {
            val uid = userId.toIntOrNull()
            if (uid != null) {
                assertThat(this.userId, _is(uid))
            }

            assertThat(this.title, containsString(title))
            assertThat(this.body, containsString(body))
        }


        //=== Mock ===
        val mockNetworkService = NetworkService_TD()

        prepareProperties(mockNetworkService)


        //=== GIVEN THAT ===
        PostsListPresenter_SUT = PostsListPresenter(postsRepository, schedulerProvider, mockView)

        //=== WHEN ===
        PostsListPresenter_SUT.fetchPostsWithFilter(userId.toString(), title, body)

        //=== THEN ===
        if (userId.toIntOrNull() != null) {
            assertThat(mockNetworkService.fetchPostWithFilterCount, _is(1))
        } else {
            assertThat(mockNetworkService.fetchPostCount, _is(1))
        }

        verify(mockView, times(1)).onFetchPosts(check {

            it.first().assertValid()

            it.random().assertValid()

            it.last().assertValid()
        })
    }


    //======= Private Helpers =======
    private fun prepareProperties(networkService: NetworkService) {

        networkDataStore = NetworkDataStoreImpl(networkService, PostsModelMapper())
        postDataStore = PostsDataStoreImpl()

        dataStoreFactory = DataStoreFactory(postDataStore, networkDataStore)

        postsRepository = PostsRepositoryImpl(dataStoreFactory)

    }
}


/** Network Service TestDouble */
class NetworkService_TD : NetworkService {
    // Test Data
    private val testDataFactory = TestDataFactory()

    var fetchPostCount = 0
    var fetchPostWithFilterCount = 0

    override fun fetchPosts(): Flowable<List<PostModel>> {
        fetchPostCount++
        return Flowable.just(testDataFactory.getPostModelList())
    }

    override fun fetchPostsWithFilter(userId: Int): Flowable<List<PostModel>> {
        fetchPostWithFilterCount++
        return Flowable.just(testDataFactory.getFilteredPostModelList(userId))
    }
}