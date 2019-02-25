package com.sumitanantwar.postsbrowser.network.network

import com.nhaarman.mockitokotlin2.*
import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.model.PostModel
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import com.sumitanantwar.postsbrowser.network.testdata.TestDataFactory
import io.reactivex.Flowable
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class NetworkDataStoreImplTests {

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

        // Mocks
        val mockNetworkService = NetworkService_TD()

        //=== GIVEN THAT ===
        NetworkDataStoreImpl_SUT = NetworkDataStoreImpl(mockNetworkService, PostsModelMapper())

        //=== WHEN ===
        val postsFlowable = NetworkDataStoreImpl_SUT.fetchAllPosts()

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        assertThat(mockNetworkService.fetchPostCount, _is(1))

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

        testFilteredPostFetch("2")
        testFilteredPostFetch("3", "est")
        testFilteredPostFetch("4", body = "sed")
        testFilteredPostFetch( body = "sed")
    }

    private fun testFilteredPostFetch(userId: String = "", title: String = "", body: String = "") {

        // Internal function to assert a valid Post
        fun Post.assertValid() : Boolean {
            val uid = userId.toIntOrNull()
            var isValid = false
            if (uid != null) {
                isValid = (this.userId == uid)
            }
            isValid = this.title.contains(title) && this.body.contains(body)

            return isValid
        }


        // Mocks
        val mockNetworkService = NetworkService_TD()

        //=== GIVEN THAT ===
        NetworkDataStoreImpl_SUT = NetworkDataStoreImpl(mockNetworkService, PostsModelMapper())

        //=== WHEN ===
        val postsFlowable = NetworkDataStoreImpl_SUT.fetchPostsWithFilter(userId, title, body)

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        val uid = userId.toIntOrNull()
        if (uid != null) {
            assertThat(mockNetworkService.fetchPostWithFilterCount, _is(1))
        } else {
            assertThat(mockNetworkService.fetchPostCount, _is(1))
        }

        // Verify the returned results
        postsFlowable.test()
            .assertSubscribed()
            .assertValue {
                it.first().assertValid()
            }
            .assertValue {
                it.random().assertValid()
            }
            .assertValue {
                it.last().assertValid()
            }
            .assertComplete()
            .assertNoErrors()
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