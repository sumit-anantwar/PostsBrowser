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
     * Fetch Posts from Remote
     */
    override fun fetchAllPosts() : Observable<List<Post>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchAllPosts()
            .toObservable()
            .distinctUntilChanged()

    }


    override fun getFavoritePosts() : Observable<List<Post>> {

        return dataStoreFactory.getpostsDataStore()
            .getFavoritePosts()

    }


}