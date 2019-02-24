package com.sumitanantwar.postsbrowser.data

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.store.DataStoreFactory
import com.sumitanantwar.postsbrowser.data.repository.PostsRepository
import io.reactivex.Observable

import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val dataStoreFactory: DataStoreFactory
) : PostsRepository {

    /**
     * Fetch posts filtered using [userId], [title] and [body]
     */
    override fun fetchPostsWithFilter(userId: String, title: String, body: String): Observable<List<Post>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchPostsWithFilter(userId, title, body)
            .toObservable()
            .distinctUntilChanged()
    }


    override fun getFavoritePosts() : Observable<List<Post>> {

        return dataStoreFactory.getpostsDataStore()
            .getFavoritePosts()

    }


}