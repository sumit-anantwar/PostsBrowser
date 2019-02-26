package com.sumitanantwar.postsbrowser.network.testdata

import com.sumitanantwar.postsbrowser.network.model.PostModel
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import io.reactivex.Flowable

/** Network Service TestDouble */
class MockNetworkService : NetworkService {
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