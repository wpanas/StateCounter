package com.github.wpanas.statecounter


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val counterActivityLinkButton = onView(withId(R.id.counter_activity_link))
        val timerActivityLinkButton = onView(withId(R.id.timer_activity_link))
        counterActivityLinkButton.check(matches(isDisplayed()))
        timerActivityLinkButton.check(matches(isDisplayed()))
    }
}
