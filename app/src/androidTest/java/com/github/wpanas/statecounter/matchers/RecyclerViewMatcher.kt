package com.github.wpanas.statecounter.matchers

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {
    fun atPosition(position: Int, targetViewId: Int = -1): Matcher<View> = object : TypeSafeMatcher<View>() {
        private var resources: Resources? = null
        private var childView: View? = null

        override fun describeTo(description: Description?) {
            var idDescription = recyclerViewId.toString()

            resources?.run {
                idDescription = try {
                    getResourceName(recyclerViewId)
                } catch (e: Resources.NotFoundException) {
                    "$recyclerViewId (resource name not found)"
                }
            }

            description?.appendText("with id: $idDescription")
        }

        override fun matchesSafely(item: View?): Boolean {
            resources = item?.resources

            if (childView == null) {
                val recyclerView: RecyclerView? = item?.rootView?.findViewById(recyclerViewId)

                if (recyclerView != null && recyclerView.id == recyclerViewId) {
                    childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                } else {
                    return false
                }
            }

            return if (targetViewId == -1) {
                item == childView
            } else {
                item == childView?.findViewById(targetViewId)
            }
        }
    }

    companion object {
        fun onRecyclerView(recyclerViewId: Int): RecyclerViewMatcher = RecyclerViewMatcher(recyclerViewId)
    }
}