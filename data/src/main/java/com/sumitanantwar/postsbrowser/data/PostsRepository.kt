package com.sumitanantwar.postsbrowser.data

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.store.DataStoreFactory
import io.reactivex.Observable

import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val dataStoreFactory: DataStoreFactory
) {

    /**
     * Fetch Posts from Remote
     */
    fun fetchAllPosts() : Observable<List<Post>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchAllPosts()
            .toObservable()
            .distinctUntilChanged()

    }


    fun getFavoritePosts() : Observable<List<Post>> {

        return dataStoreFactory.getpostsDataStore()
            .getFavoritePosts()

    }


}