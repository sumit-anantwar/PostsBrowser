package com.sumitanantwar.postsbrowser.mobile.helpers

import android.support.v7.widget.RecyclerView
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import org.hamcrest.Description

import org.hamcrest.Matcher


fun hasItemAtPosition(position: Int, itemMatcher: Matcher<View>, targetViewId: Int): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }

        public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}