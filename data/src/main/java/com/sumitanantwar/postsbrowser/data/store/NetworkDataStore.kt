package com.sumitanantwar.postsbrowser.data.store

import com.sumitanantwar.postsbrowser.data.model.Post
import io.reactivex.Flowable

interface NetworkDataStore {

    fun fetchAllPosts() : Flowable<List<Post>>

}