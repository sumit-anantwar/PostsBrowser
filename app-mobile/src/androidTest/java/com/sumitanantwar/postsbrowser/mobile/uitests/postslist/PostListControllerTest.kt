package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist


import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.sumitanantwar.postsbrowser.mobile.testapp.TestApp
import com.sumitanantwar.postsbrowser.mobile.testdata.UiTestDataFactory
import com.sumitanantwar.postsbrowser.mobile.ui.activities.main.MainActivity
import com.sumitanantwar.postsbrowser.mobile.uitests.makeActivityTestRule
import com.sumitanantwar.postsbrowser.mobile.uitests.postslist.withPostsListBrowserRobot
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PostListControllerTest {

    @get:Rule
    val activityTestRule = makeActivityTestRule<MainActivity>()

    // Test Data Factory
    private val testDataFactory = UiTestDataFactory()

    @Before
    fun setup() {
    }

    @Test
    fun test_ActivityLaunchesWithoutErrors() {
        // Stub Fetch Posts Response
        stub_PostsRepository_FetchPosts()

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()
        }
    }

    @Test
    fun test_PostListController_ViewInteractions() {
        // Stub Fetch Posts Response
        stub_PostsRepository_FetchPosts()

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()
            checkRecyclerIsDisplayed()

            repeat(10) {
                checkFilterLayoutIsHidden()
                clickOnFilterButton()
                checkFilterLayoutIsDisplayed()
                clickOnFilterButton()
            }
        }
    }


    @Test
    fun test_PostListController_PostsList() {
        // Stub Fetch Posts Response
        stub_PostsRepository_FetchPosts()

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()
            checkRecyclerIsDisplayed()
            checkRecyclerViewAtPositionHasUserId(2, 1)
            checkRecyclerViewAtPositionHasUserId(11, 2)
        }
    }

    //======= Stubbed Responses =======
    private fun stub_PostsRepository_FetchPosts(userId: Int? = null) {
        whenever(
            TestApp.appComponent().postsRepository().fetchPostsWithFilter(any(), any(), any())
        ) doReturn (
                Observable.just(testDataFactory.makePostsList(userId))
        )
    }

}
