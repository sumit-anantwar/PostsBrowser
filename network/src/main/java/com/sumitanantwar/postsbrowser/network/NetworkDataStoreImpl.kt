package com.sumitanantwar.postsbrowser.network

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.store.NetworkDataStore
import com.sumitanantwar.postsbrowser.network.mapper.PostsModelMapper
import com.sumitanantwar.postsbrowser.network.service.NetworkService
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class NetworkDataStoreImpl @Inject constructor(
    private val networkService: NetworkService,
    private val postModelMapper: PostsModelMapper
) : NetworkDataStore {

    override fun fetchAllPosts(): Flowable<List<Post>> {
        return networkService.fetchPosts()
            .map {
                it.map {
                    postModelMapper.mapFromModel(it)
                }
            }
    }

    override fun fetchPostsWithFilter(userId: String, title: String, body: String): Flowable<List<Post>> {

        // Internal function to apply filter
        fun Flowable<List<Post>>.applyFilter() : Flowable<List<Post>> {
            return this.map {
                it.filter {
                    it.title.contains(title) &&
                            it.body.contains(body)
                }
            }
        }

        val uid = userId.toIntOrNull()
        if (uid == null) {
            return fetchAllPosts().applyFilter()
        } else {
            return fetchPostsWithUserId(uid).applyFilter()
        }


    }

    private fun fetchPostsWithUserId(userId: Int) : Flowable<List<Post>> {
        return networkService.fetchPostsWithFilter(userId)
            .map {
                it.map {
                    postModelMapper.mapFromModel(it)
                }
            }
    }

}