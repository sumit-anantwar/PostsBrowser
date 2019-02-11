package com.sumitanantwar.postsbrowser.network.mapper

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.network.model.PostModel
import javax.inject.Inject

class PostsModelMapper @Inject constructor() : ModelMapper<PostModel, Post> {

    override fun mapFromModel(model: PostModel): Post {
        return Post(model.id, model.userId, model.title, model.body)
    }
}