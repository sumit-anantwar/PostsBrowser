package com.sumitanantwar.postsbrowser.mobile.uitests.postslist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.helpers.hasItemAtPosition
import com.sumitanantwar.postsbrowser.mobile.ui.activities.main.MainActivity
import com.sumitanantwar.postsbrowser.mobile.uitests.BaseRobot
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not

fun withPostsListBrowserRobot(activityTestRule: ActivityTestRule<MainActivity>, func: PostListBrowserRobot.() -> Unit) = PostListBrowserRobot(activityTestRule).apply { func() }

class PostListBrowserRobot(activityTestRule: ActivityTestRule<MainActivity>) : BaseRobot<MainActivity>(activityTestRule) {

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

    fun scrollRecyclerViewToPosition(position: Int) {
        onView(
            withId(R.id.recycler_posts)
        ).perform(
            scrollToPosition<RecyclerView.ViewHolder>(position)
        )
    }

    fun checkRecyclerViewAtPositionHasUserId(position: Int, userId: Int) {
        checkRecyclerViewAtPositionMatchesFilter(position, userId)
    }

    fun checkRecyclerViewAtPositionMatchesFilter(position: Int, userIdFilter: Int, titleFilter: String = "") {
        scrollRecyclerViewToPosition(position)

        onView(
            withId(R.id.recycler_posts)
        ).check(
            matches(hasItemAtPosition(position, withText(userIdFilter.toString()), R.id.text_userid))
        )
    }

    fun typeUserIdFilter(filter: String) {
        editText_Type(R.id.edit_text_userid, filter)
    }
}