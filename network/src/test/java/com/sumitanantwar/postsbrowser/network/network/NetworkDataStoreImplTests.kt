package com.sumitanantwar.postsbrowser.network.network

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.sumitanantwar.postsbrowser.network.NetworkDataStoreImpl
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import com.sumitanantwar.postsbrowser.network.testdata.TestDataFactory
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.MatcherAssert.assertThat
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
    fun test_FetchPosts_Returns_ListOfPostModels() {
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

}