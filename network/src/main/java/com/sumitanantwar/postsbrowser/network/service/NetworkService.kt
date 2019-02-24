package com.sumitanantwar.postsbrowser.network.service

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.network.model.PostModel
import com.sumitanantwar.postsbrowser.network.model.response.PostResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/posts")
    fun fetchPosts() : Flowable<List<PostModel>>

    @GET("/posts")
    fun fetchPostsWithFilter(@Query("userId") userId: Int) : Flowable<List<PostModel>>

}