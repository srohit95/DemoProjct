package com.example.mainproject1.homescreen


import android.view.View
import androidx.appcompat.widget.AppCompatSpinner
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.mainproject1.MainActivity
import com.example.mainproject1.R
import com.example.mainproject1.adapters.ProductListAdapter
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeScreenTest{

    @Test
    fun testHomeActivity_inView() {
        val activityScenario = ActivityScenario.launch(HomeScreen::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.homeScreen))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }

    @Test
    fun testHomeActivity_RecyclerView_inView() {
        val activityScenario = ActivityScenario.launch(HomeScreen::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.homeScreen))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testProjectExit_inView() {
        val activityScenario = ActivityScenario.launch(HomeScreen::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.homeScreen)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()
        onView(withText("Wish to exit application?")).check(matches(isDisplayed()))
        onView(withText("Yes")).perform(click())

    }




}