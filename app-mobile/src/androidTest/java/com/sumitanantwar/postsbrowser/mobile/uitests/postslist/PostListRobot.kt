package com.sumitanantwar.postsbrowser.mobile.uitests.postslist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.uitests.BaseRobot
import org.hamcrest.CoreMatchers.not

fun withPostsListBrowserRobot(func: PostListBrowserRobot.() -> Unit) = PostListBrowserRobot().apply { func() }

class PostListBrowserRobot : BaseRobot() {

    fun checkRecyclerIsDisplayed() {
        onView(
            withId(R.id.recycler_posts)
        ).check(
            matches(isDisplayed())
        )
    }

    fun checkFilterLayoutIsHidden() {
        onView(
            withId(R.id.layout_filter)
        ).check(
            matches(not(isDisplayed()))
        )
    }

    fun checkFilterLayoutIsDisplayed() {
        onView(
            withId(R.id.layout_filter)
        ).check(
            matches(isDisplayed())
        )
    }

    fun clickOnFilterButton() {
        onView(
            withId(R.id.button_filter)
        ).check(
            matches(isDisplayed())
        ).perform(
            click()
        )
    }
}