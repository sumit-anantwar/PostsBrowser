package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist


import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.sumitanantwar.postsbrowser.mobile.testdata.UiTestDataFactory
import com.sumitanantwar.postsbrowser.mobile.ui.activities.main.MainActivity
import com.sumitanantwar.postsbrowser.mobile.uitests.makeActivityTestRule
import com.sumitanantwar.postsbrowser.mobile.uitests.postslist.withPostsListBrowserRobot
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

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()
        }
    }

    @Test
    fun test_PostListController_ViewInteractions() {

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

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()

            checkRecyclerIsDisplayed()

            checkRecyclerViewAtPositionHasUserId(2, 1)
            checkRecyclerViewAtPositionHasUserId(11, 2)
        }
    }

    @Test
    fun test_PostListController_FilterWithUserId() {

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()

            checkRecyclerIsDisplayed()

            checkRecyclerViewAtPositionHasUserId(2, 1)
            checkRecyclerViewAtPositionHasUserId(11, 2)

            checkFilterLayoutIsHidden()
            clickOnFilterButton()
            checkFilterLayoutIsDisplayed()

            clearUserIdFilter()
            clearTitleFilter()

            typeInUserIdFilter("2")
            checkRecyclerViewAtPositionHasUserId(1, 2)
            checkRecyclerViewAtPositionHasUserId(2, 2)
            checkRecyclerViewAtPositionHasUserId(3, 2)
        }
    }

    @Test
    fun test_PostListController_FilterWithTitle() {

        withPostsListBrowserRobot(activityTestRule) {
            launchActivity()

            checkRecyclerIsDisplayed()

            checkRecyclerViewAtPositionHasUserId(2, 1)
            checkRecyclerViewAtPositionHasUserId(11, 2)

            checkFilterLayoutIsHidden()
            clickOnFilterButton()
            checkFilterLayoutIsDisplayed()

            clearUserIdFilter()
            clearTitleFilter()

            typeInTitleFilter("qui")
            checkRecyclerViewAtPositionContainsStringInTitle(1, "qui")
            checkRecyclerViewAtPositionContainsStringInTitle(2, "qui")
            checkRecyclerViewAtPositionContainsStringInTitle(3, "qui")

            typeInTitleFilter(" ")
            checkRecyclerViewAtPositionContainsStringInTitle(1, "qui ")
            checkRecyclerViewAtPositionContainsStringInTitle(2, "qui ")
            checkRecyclerViewAtPositionContainsStringInTitle(3, "qui ")

            typeInTitleFilter("e")
            checkRecyclerViewAtPositionContainsStringInTitle(1, "qui e")
            checkRecyclerViewAtPositionContainsStringInTitle(2, "qui e")
            checkRecyclerViewAtPositionContainsStringInTitle(3, "qui e")
        }
    }


}
