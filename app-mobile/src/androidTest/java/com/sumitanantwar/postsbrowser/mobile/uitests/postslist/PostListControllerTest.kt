package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.testapp.TestApp
import com.sumitanantwar.postsbrowser.mobile.ui.activities.main.MainActivity
import com.sumitanantwar.postsbrowser.mobile.testdata.UiTestDataFactory
import com.sumitanantwar.postsbrowser.mobile.uitests.postslist.withPostsListBrowserRobot

import io.reactivex.Observable
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PostListControllerTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    // Test Data Factory
    private val testDataFactory = UiTestDataFactory()

    @Before
    fun setup() {

    }

    @Test
    fun test_ActivityLaunchesWithoutErrors() {
        // Stub Fetch Posts Response
        stub_PostsRepository_FetchPosts()

        withPostsListBrowserRobot {
            launchActivity(activityTestRule)
        }
    }

    @Test
    fun test_PostListController_ViewInteractions() {
        // Stub Fetch Posts Response
        stub_PostsRepository_FetchPosts()

        withPostsListBrowserRobot {
            launchActivity(activityTestRule)
            checkRecyclerIsDisplayed()
            checkFilterLayoutIsHidden()
            clickOnFilterButton()
            checkFilterLayoutIsDisplayed()
            clickOnFilterButton()
            checkFilterLayoutIsHidden()
        }
    }


    //======= Stubbed Responses =======
    private fun stub_PostsRepository_FetchPosts() {
        whenever(
            TestApp.appComponent().postsRepository().fetchAllPosts()
        ) doReturn (
            Observable.just(testDataFactory.makePostsList(100))
        )
    }

}
