package com.sumitanantwar.postsbrowser.network.testdata

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sumitanantwar.postsbrowser.network.model.PostModel
import java.nio.charset.Charset

internal class TestDataFactory {


    private val postsModelList: List<PostModel> by lazy {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource("posts.json")

        val inputStream = resource.openStream()
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val jsonString = String(buffer, Charset.defaultCharset())

        val listType = object : TypeToken<List<PostModel>>() { }.type
        Gson().fromJson(jsonString, listType) as List<PostModel>
    }

    fun getPostModelList() : List<PostModel> = postsModelList

    fun getFilteredPostModelList(userId: Int) : List<PostModel> {

        val filteredList = postsModelList.filter { post ->
            (post.userId == userId)
        }

        return filteredList
    }

    fun getRandomPostModel() : PostModel {

        val rndIndex = (0..postsModelList.count()-1).random()
        return postsModelList[rndIndex]
    }

}