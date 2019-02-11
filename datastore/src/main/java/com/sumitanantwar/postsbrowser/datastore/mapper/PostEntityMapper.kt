package com.sumitanantwar.postsbrowser.datastore.mapper

import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.datastore.model.PostEntity

class PostEntityMapper : EntityMapper<PostEntity, Post> {

    override fun mapFromEntity(entity: PostEntity): Post {
        return Post(entity.id, entity.userId, entity.title, entity.body)
    }

    override fun mapToEntity(data: Post): PostEntity {
        return PostEntity(data.id, data.userId, data.title, data.body)
    }

}