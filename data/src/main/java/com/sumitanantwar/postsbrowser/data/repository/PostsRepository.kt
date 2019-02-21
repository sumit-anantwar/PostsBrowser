package com.sumitanantwar.postsbrowser.data.repository

import com.sumitanantwar.postsbrowser.data.model.Post
import io.reactivex.Observable

interface PostsRepository {
    fun fetchAllPosts() : Observable<List<Post>>
    fun getFavoritePosts() : Observable<List<Post>>
}