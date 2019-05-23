package com.example.helsanf.footballmatch.view.pertandingan.liga

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.AutoCompleteTextView
import com.example.helsanf.footballmatch.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LeugueActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(LeugueActivity::class.java)

    @Test
    fun testSearchApp() {
        Thread.sleep(3000)
        Espresso.onView(withId(R.id.recycleLiga))
            .check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.recycleLiga)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        Thread.sleep(2000)
        onView(withId(R.id.searchView)).perform(click())
        Thread.sleep(2000)
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("Chelsea"))
            .perform(pressImeActionButton())
        Thread.sleep(5000)



    }
}