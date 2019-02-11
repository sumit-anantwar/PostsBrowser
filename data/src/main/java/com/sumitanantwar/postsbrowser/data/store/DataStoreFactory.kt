package com.sumitanantwar.postsbrowser.data.store

import javax.inject.Inject

class DataStoreFactory @Inject constructor(
    private val postsDataStore: PostsDataStore,
    private val networkDataStore: NetworkDataStore) {

    fun getpostsDataStore() : PostsDataStore {
        return postsDataStore
    }

    fun getNetworkDataStore() : NetworkDataStore {
        return networkDataStore
    }
}