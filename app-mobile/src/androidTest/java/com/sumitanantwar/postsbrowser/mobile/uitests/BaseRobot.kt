package com.sumitanantwar.postsbrowser.mobile.uitests

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier

/** Makes a non-launching [ActivityTestRule]
 * so that we can stub network responses before launching the activity  */
inline fun <reified T: Activity> makeActivityTestRule() : ActivityTestRule<T> {
    return ActivityTestRule(T::class.java, false, false)
}

open class BaseRobot<A: Activity>(val activityTestRule: ActivityTestRule<A>) {

    /** Launch the [Activity] as defined by the [ActivityTestRule]*/
    fun launchActivity(startIntent: Intent? = null) {
        activityTestRule.launchActivity(startIntent)
    }

    fun editText_Replace(resId: Int, text: String): ViewInteraction =
        onView(ViewMatchers.withId(resId)).perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())

    fun editText_Type(resId: Int, text: String): ViewInteraction =
        onView(ViewMatchers.withId(resId)).perform(typeText(text), ViewActions.closeSoftKeyboard())

    fun button_Click(resId: Int): ViewInteraction = onView((ViewMatchers.withId(resId))).perform(ViewActions.click())

    fun textView(resId: Int): ViewInteraction = onView(ViewMatchers.withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(matches(withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)

    fun clickListItem(listRes: Int, position: Int) {
        Espresso.onData(Matchers.anything())
            .inAdapterView(Matchers.allOf(ViewMatchers.withId(listRes)))
            .atPosition(position).perform(ViewActions.click())
    }

    fun sleep() = apply {
        Thread.sleep(500)
    }

}