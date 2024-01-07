package zechs.music

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

object TabLayoutMatcher {
    fun hasTabCount(expectedCount: Int): Matcher<View> {
        return object : BoundedMatcher<View, TabLayout>(TabLayout::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("TabLayout with tab count: $expectedCount")
            }

            override fun matchesSafely(item: TabLayout?): Boolean {
                return item?.tabCount == expectedCount
            }
        }
    }
}
