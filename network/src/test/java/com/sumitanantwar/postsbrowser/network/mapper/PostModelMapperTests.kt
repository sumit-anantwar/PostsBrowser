package com.sumitanantwar.postsbrowser.network.mapper


import com.sumitanantwar.postsbrowser.network.testdata.TestDataFactory
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class PostModelMapperTests {

    private val testDataFactory = TestDataFactory()

    // SUT
    private val PostsModelMapper_SUT = PostsModelMapper()


    @Test
    fun test_PostModelMapper_Maps_PostModel_To_Post() {

        // GIVEN THAT
        val postModel = testDataFactory.getRandomPostModel()

        // WHEN
        val post = PostsModelMapper_SUT.mapFromModel(postModel)

        // THEN
        assertThat(post.id, _is(postModel.id))
        assertThat(post.userId, _is(postModel.userId))
        assertThat(post.title, _is(postModel.title))
        assertThat(post.body, _is(postModel.body))
    }

}