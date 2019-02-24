package com.sumitanantwar.postsbrowser.data.repository

import com.sumitanantwar.postsbrowser.data.model.Post
import io.reactivex.Observable

interface PostsRepository {
    fun fetchPostsWithFilter(userId: String, title: String, body: String) : Observable<List<Post>>
    fun getFavoritePosts() : Observable<List<Post>>
}