package com.sumitanantwar.postsbrowser.datastore

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.data.store.PostsDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class PostsDataStoreImpl @Inject constructor() : PostsDataStore {
    override fun clearFavorites(): Completable {
        return Completable.defer {
            // TODO: Not Implemented
            Completable.complete()
        }
    }

    override fun getFavoritePosts(): Observable<List<Post>> {
        // TODO: Not Implemented
        return Observable.just(listOf(Post(0, 0, "", "")))
    }

    override fun setPostAsFavorite(postId: Int): Completable {
        return Completable.defer {
            // TODO: Not Implemented
            Completable.complete()
        }
    }

    override fun setPostAsNotFavorite(postId: Int): Completable {
        return Completable.defer {
            // TODO: Not Implemented
            Completable.complete()
        }
    }
}