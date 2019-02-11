package com.sumitanantwar.postsbrowser.mobile.postslist

import com.nhaarman.mockitokotlin2.*
import com.sumitanantwar.postsbrowser.data.PostsRepository
import com.sumitanantwar.postsbrowser.data.store.DataStoreFactory
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.data.store.PostsDataStore
import com.sumitanantwar.postsbrowser.datastore.PostsDataStoreImpl
import com.sumitanantwar.postsbrowser.mobile.scheduler.ImmediateSchedulerProvider
import com.sumitanantwar.postsbrowser.mobile.testdata.TestDataFactory
import com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist.PostsListContract
import com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist.PostsListPresenter
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import io.reactivex.Flowable
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as _is



class PostsListPresenterTests {

    // TestDataFactory
    private val testDataFactory = TestDataFactory()

    private lateinit var mockView: PostsListContract.View

    private lateinit var mockNetworkService: NetworkService
    private lateinit var postsRepository: PostsRepository
    private lateinit var dataStoreFactory: DataStoreFactory
    private lateinit var networkDataStore: NetworkDataStore
    private lateinit var postDataStore: PostsDataStore

    // Scheduler
    private val schedulerProvider = ImmediateSchedulerProvider()

    // SUT
    private lateinit var PostsListPresenter_SUT : PostsListPresenter

    @Before
    fun setup() {

        //=== MOCK ===
        mockView = mock { }
        mockNetworkService = mock {
            on { fetchPosts() } doReturn Flowable.just(testDataFactory.getPostModelList())
        }

        networkDataStore = NetworkDataStoreImpl(mockNetworkService, PostsModelMapper())
        postDataStore = PostsDataStoreImpl()

        dataStoreFactory = DataStoreFactory(postDataStore, networkDataStore)

        postsRepository = PostsRepository(dataStoreFactory)
    }

    @After
    fun tearDown(){

    }


    @Test
    fun test_FetchPosts_Returns_ListOfPosts() {

        //=== GIVEN THAT ===
        PostsListPresenter_SUT = PostsListPresenter(postsRepository, schedulerProvider, mockView)

        //=== WHEN ===
        PostsListPresenter_SUT.fetchPosts()

        //=== THEN ===
        verify(mockNetworkService, times(1)).fetchPosts()
        verify(mockView, times(1)).onFetchPosts(check {
            assertThat(it.count(), _is(10))
            assertThat(it.first().id, _is(1) )
            assertThat(it.last().id, _is(10) )
        })

    }


}