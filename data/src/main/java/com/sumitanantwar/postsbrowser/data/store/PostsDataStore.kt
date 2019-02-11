package com.sumitanantwar.postsbrowser.data.store

import com.sumitanantwar.postsbrowser.data.model.Post
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface PostsDataStore {

    fun clearFavorites() : Completable

    fun getFavoritePosts() : Observable<List<Post>>

    fun setPostAsFavorite(postId: Int) : Completable

    fun setPostAsNotFavorite(postId: Int) : Completable
}