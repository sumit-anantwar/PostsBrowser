package com.sumitanantwar.postsbrowser.mobile.testdata

import com.sumitanantwar.postsbrowser.data.model.Post

internal class UiTestDataFactory {

    fun makePostsList(count: Int) : List<Post> {
        val postList = mutableListOf<Post>()
        for (i in 1..count) {
            val uid = (1 % 10) + 1
            postList.add(Post(i, uid, "Title $i", "Body $i" ))
        }

        return postList
    }
}