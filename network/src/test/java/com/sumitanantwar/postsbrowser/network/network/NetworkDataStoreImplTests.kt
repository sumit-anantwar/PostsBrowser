package com.sumitanantwar.postsbrowser.network.network

import com.nhaarman.mockitokotlin2.*
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import com.sumitanantwar.postsbrowser.network.testdata.TestDataFactory
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class NetworkDataStoreImplTests {

    // Test Data
    private val testDataFactory = TestDataFactory()

    // Mocks
    private lateinit var mockNetworkService: NetworkService

    // SUT
    private lateinit var NetworkDataStoreImpl_SUT: NetworkDataStoreImpl

    @Before
    fun setup() {

    }

    @After
    fun teardown() {

    }

    @Test
    fun test_FetchPosts_Returns_ListOfPosts() {
        //=== MOCK ===
        mockNetworkService = mock {
            on { fetchPosts() } doReturn Flowable.just(testDataFactory.getPostModelList())
        }

        //=== GIVEN THAT ===
        NetworkDataStoreImpl_SUT = NetworkDataStoreImpl(mockNetworkService, PostsModelMapper())

        //=== WHEN ===
        val postsFlowable = NetworkDataStoreImpl_SUT.fetchAllPosts()

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        verify(mockNetworkService, times(1)).fetchPosts()

        // Verify the returned results
        postsFlowable.test()
            .assertSubscribed()
            .assertValue {
                it.count() == 10
            }
            .assertValue {
                it.first().id == 1
            }
            .assertValue {
                it.last().id == 10
            }
            .assertComplete()
            .assertNoErrors()

    }

    @Test
    fun test_FetchPostsWithFilter_Returns_FilteredListOfPosts() {

        testFilteredPostFetch(2)
        testFilteredPostFetch(3, "est")
        testFilteredPostFetch(4, body = "sed")
    }

    private fun testFilteredPostFetch(userId: Int, title: String = "", body: String = "") {
        //=== MOCK ===
        mockNetworkService = mock {
            on { fetchPostsWithFilter(any()) } doReturn Flowable.just(testDataFactory.getFilteredPostModelList(userId))
        }

        //=== GIVEN THAT ===
        NetworkDataStoreImpl_SUT = NetworkDataStoreImpl(mockNetworkService, PostsModelMapper())

        //=== WHEN ===
        val postsFlowable = NetworkDataStoreImpl_SUT.fetchPostsWithFilter(userId.toString(), title, body)

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        verify(mockNetworkService, times(1)).fetchPostsWithFilter(any())

        // Verify the returned results
        postsFlowable.test()
            .assertSubscribed()
            .assertValue {
                with(it.first()){
                    (this.userId == userId) &&
                            this.title.contains(title) &&
                            this.body.contains(body)
                }

            }
            .assertValue {
                with(it.random()) {
                    (this.userId == userId) &&
                            this.title.contains(title) &&
                            this.body.contains(body)
                }
            }
            .assertValue {
                with(it.last()){
                    (this.userId == userId) &&
                            this.title.contains(title) &&
                            this.body.contains(body)
                }
            }
            .assertComplete()
            .assertNoErrors()
    }

}