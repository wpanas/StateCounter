package com.github.wpanas.statecounter.counter


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.github.wpanas.statecounter.R
import com.github.wpanas.statecounter.action.ActionRoomDatabase
import com.github.wpanas.statecounter.matchers.RecyclerViewMatcher.Companion.onRecyclerView
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CounterActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(CounterActivity::class.java)

    @Test
    fun counterActivityTest() {
        val saveButton = onView(withId(R.id.save_counter))
        val plusButton = onView(withId(R.id.plus_button))
        val minusButton = onView(withId(R.id.minus_button))
        val counter = onView(withId(R.id.counter))
        val recyclerView = onRecyclerView(R.id.action_recycler)

        plusButton
            .perform(click())
            .perform(click())

        counter.check(matches(withText("2")))

        saveButton
            .perform(click())

        onView(recyclerView.atPosition(0, R.id.action_item_text))
            .check(matches(withText("2")))

        minusButton
            .perform(click())

        counter.check(matches(withText("1")))

        saveButton
            .perform(click())

        onView(recyclerView.atPosition(0, R.id.action_item_text))
            .check(matches(withText("1")))

        onView(recyclerView.atPosition(1, R.id.action_item_text))
            .check(matches(withText("2")))
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            instrumentation.targetContext.deleteDatabase(ActionRoomDatabase.DATABASE_NAME)
        }
    }
}