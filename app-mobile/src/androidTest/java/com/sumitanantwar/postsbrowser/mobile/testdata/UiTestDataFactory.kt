package com.sumitanantwar.postsbrowser.mobile.testdata

import com.sumitanantwar.postsbrowser.data.model.Post

internal class UiTestDataFactory {

    private val postList by lazy {
        val postList = mutableListOf<Post>()
        for (i in 1..100) {
            val uid = (i/10) + 1
            postList.add(Post(i, uid, "Title $i", "Body $i" ))
        }
        postList
    }

    fun makePostsList(userId: Int? = null) : List<Post> {
        if (userId != null) {
            return postList.filter { it.userId == userId }
        }

        return postList
    }
}