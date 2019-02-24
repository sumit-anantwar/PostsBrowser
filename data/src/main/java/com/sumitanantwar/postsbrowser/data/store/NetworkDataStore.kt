package com.sumitanantwar.postsbrowser.data.store

import com.sumitanantwar.postsbrowser.data.model.Post
import io.reactivex.Flowable
import io.reactivex.Observable

interface NetworkDataStore {

    fun fetchAllPosts() : Flowable<List<Post>>
    fun fetchPostsWithFilter(userId: String, title: String, body: String) : Flowable<List<Post>>

}